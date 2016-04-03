package com.thaer.jj.model;

import com.thaer.jj.core.utils.Strings;
import com.thaer.jj.entities.OfferStockDetail;
import com.thaer.jj.entities.User;
import com.thaer.jj.model.responseData.OfferViewResponse;
import com.thaer.jj.model.responseData.OffersListResponse;
import com.thaer.jj.model.sets.OfferDetails;
import com.thaer.jj.model.sets.OfferOptionDetail;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Mar 11, 2016.
 */
public class OfferModel extends AbstractModel {

    public OfferModel() throws SQLException {
    }

    public ArrayList<OffersListResponse> getOfferDetailList() throws SQLException {
        ResultSet resultSet = executeQuery("SELECT o.id, oo.id, oo.picture, o.title FROM `offers_options` oo INNER JOIN `offers` o ON oo.offer_id = o.id WHERE oo.status = 'live' AND o.status = 'live' GROUP BY oo.offer_id ORDER BY oo.offer_id DESC LIMIT 20");

        ArrayList<OffersListResponse> ResponseList = new ArrayList<>();
        ArrayList<Integer> offersOptionsIds = new ArrayList<>();
        OffersListResponse offersListResponse;

        while (resultSet.next()) {
            offersListResponse = new OffersListResponse();

            offersListResponse.offerId = resultSet.getInt("o.id");
            offersListResponse.title = resultSet.getString("o.title");
            offersListResponse.offerOptionId = resultSet.getInt("oo.id");
            offersListResponse.picture = resultSet.getString("oo.picture");

            offersOptionsIds.add(offersListResponse.offerOptionId);
            ResponseList.add(offersListResponse);
        }

        String offersOptionsIdsStr = Strings.implode(", ", offersOptionsIds);

        resultSet = executeQuery("SELECT offer_option_id, price FROM offers_stock_details WHERE stock_quantity > 0 AND offer_option_id IN (" + offersOptionsIdsStr + ")");

        HashMap<Integer, ArrayList<BigDecimal>> pricesMap = new HashMap<>();
        ArrayList<BigDecimal> prices;
        while(resultSet.next()) {
            int offerOptionId = resultSet.getInt("offer_option_id");

            if(!pricesMap.containsKey(offerOptionId)) {
                prices = new ArrayList<>();
                pricesMap.put(resultSet.getInt("offer_option_id"), prices);
            }

            pricesMap.get(resultSet.getInt("offer_option_id")).add(resultSet.getBigDecimal("price"));
        }

        for(OffersListResponse offersListRes : ResponseList) {
            prices = pricesMap.get(offersListRes.offerOptionId);

            for(BigDecimal price : prices) {
                offersListRes.maxPrice = (offersListRes.maxPrice == null || offersListRes.maxPrice.compareTo(price) == -1) ? price : offersListRes.maxPrice;
                offersListRes.minPrice = (offersListRes.minPrice == null || offersListRes.minPrice.compareTo(price) == 1) ? price : offersListRes.minPrice;
            }
        }

        return ResponseList;
    }

    public OfferViewResponse getOfferDetailsByOptionId(int offerId, int optionId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT oo.id, oo.picture, oo.color, o.title, o.description FROM `offers_options` oo INNER JOIN `offers` o ON oo.offer_id = o.id WHERE o.id = " + offerId + " AND oo.status = 'live' AND o.status = 'live'");

        OfferViewResponse offerViewResponse = new OfferViewResponse();
        OfferDetails offerDetails = new OfferDetails();
        offerDetails.offerOptionsDetails = new ArrayList<>();

        HashMap<Integer, String> variationsPictures = new HashMap<>();
        HashMap<Integer, String> variationsColors = new HashMap<>();

        while (resultSet.next()) {
            if(resultSet.getInt("oo.id") == optionId) {
                offerViewResponse.optionId = resultSet.getInt("oo.id");
                offerViewResponse.title = resultSet.getString("o.title");
                offerViewResponse.description = resultSet.getString("o.description");
                offerViewResponse.picture = resultSet.getString("oo.picture");
                offerViewResponse.color = resultSet.getString("oo.color");
            }

            variationsPictures.put(resultSet.getInt("oo.id"), resultSet.getString("oo.picture"));
            variationsColors.put(resultSet.getInt("oo.id"), resultSet.getString("oo.color"));

        }

        offerViewResponse.variationsPictures = variationsPictures;
        offerViewResponse.variationsColors = variationsColors;

        OffersStockDetailsModel offersStockDetailsModel = new OffersStockDetailsModel();
        offerViewResponse.offerStockDetails = offersStockDetailsModel.getOfferStockDetailsByOptionId(optionId);

        return offerViewResponse;
    }

    public boolean addOffers(User seller, OfferDetails offerDetails) throws SQLException, IllegalArgumentException {

        dbCconnection.setAutoCommit(false);

        try {

            // Add offer
            String query = "INSERT INTO `offers` (`seller_id`, `category_id`, `brand_id`, `title`, `description`, `status`) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, seller.getId());
            preparedStatement.setInt(2, offerDetails.offer.getCategoryId());
            preparedStatement.setInt(3, offerDetails.offer.getBrandId());
            preparedStatement.setString(4, offerDetails.offer.getTitle());
            preparedStatement.setString(5, offerDetails.offer.getDescription());
            preparedStatement.setString(6, offerDetails.offer.getStatus());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            int newOfferId;

            if(resultSet.next()) {
                newOfferId = resultSet.getInt(1);
                offerDetails.setNewOfferId(newOfferId);
            }else {
                throw new IllegalArgumentException();
            }

            if(offerDetails.offerOptionsDetails.size() == 0) {
                throw new IllegalArgumentException();
            }

            // Add OfferOptions
            OfferOptionModel offerOptionModel = new OfferOptionModel();
            for(OfferOptionDetail offerOptionDetail : offerDetails.offerOptionsDetails) {
                int newOfferOptionId = offerOptionModel.addOfferOption(offerOptionDetail.offerOption);

                if(newOfferOptionId > 0) {
                    offerOptionDetail.setNewOfferOptionId(newOfferOptionId);
                }else {
                    throw new IllegalArgumentException();
                }

                // Add OfferStockDetail
                OffersStockDetailsModel offersStockDetailsModel = new OffersStockDetailsModel();
                for (OfferStockDetail offerStockDetail : offerOptionDetail.offerStockDetails) {
                    offersStockDetailsModel.addOfferPrice(offerStockDetail);
                }

            }

            dbCconnection.commit();

            return true;

        }catch (SQLException e) {
            dbCconnection.rollback();
            dbCconnection.setAutoCommit(true);
            throw e;
        }

    }

}
