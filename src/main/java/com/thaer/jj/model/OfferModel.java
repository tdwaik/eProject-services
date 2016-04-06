package com.thaer.jj.model;

import com.thaer.jj.core.utils.Strings;
import com.thaer.jj.entities.OfferStockDetail;
import com.thaer.jj.entities.User;
import com.thaer.jj.model.responseData.OfferViewResponse;
import com.thaer.jj.model.responseData.OffersListResponse;
import com.thaer.jj.model.sets.OfferDetails;
import com.thaer.jj.model.sets.VariationDetails;

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
        ResultSet resultSet = executeQuery("SELECT o.id, ov.id, ov.picture, o.title FROM `offers_variations` ov INNER JOIN `offers` o ON ov.offer_id = o.id WHERE ov.status = 'live' AND o.status = 'live' GROUP BY ov.offer_id ORDER BY ov.offer_id DESC LIMIT 20");

        ArrayList<OffersListResponse> ResponseList = new ArrayList<>();
        ArrayList<Integer> variationsIds = new ArrayList<>();
        OffersListResponse offersListResponse;

        while (resultSet.next()) {
            offersListResponse = new OffersListResponse();

            offersListResponse.offerId = resultSet.getInt("o.id");
            offersListResponse.title = resultSet.getString("o.title");
            offersListResponse.variationId = resultSet.getInt("ov.id");
            offersListResponse.picture = resultSet.getString("ov.picture");

            variationsIds.add(offersListResponse.variationId);
            ResponseList.add(offersListResponse);
        }

        String variationsIdsStr = Strings.implode(", ", variationsIds);

        resultSet = executeQuery("SELECT variation_id, price FROM offers_stock_details WHERE stock_quantity > 0 AND variation_id IN (" + variationsIdsStr + ")");

        HashMap<Integer, ArrayList<BigDecimal>> pricesMap = new HashMap<>();
        ArrayList<BigDecimal> prices;
        while(resultSet.next()) {
            int variationId = resultSet.getInt("variation_id");

            if(!pricesMap.containsKey(variationId)) {
                prices = new ArrayList<>();
                pricesMap.put(resultSet.getInt("variation_id"), prices);
            }

            pricesMap.get(resultSet.getInt("variation_id")).add(resultSet.getBigDecimal("price"));
        }

        for(OffersListResponse offersListRes : ResponseList) {
            prices = pricesMap.get(offersListRes.variationId);

            for(BigDecimal price : prices) {
                offersListRes.maxPrice = (offersListRes.maxPrice == null || offersListRes.maxPrice.compareTo(price) == -1) ? price : offersListRes.maxPrice;
                offersListRes.minPrice = (offersListRes.minPrice == null || offersListRes.minPrice.compareTo(price) == 1) ? price : offersListRes.minPrice;
            }
        }

        return ResponseList;
    }

    public OfferViewResponse getOfferDetailsByVariationId(int offerId, int variationId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT ov.id, ov.picture, ov.color, o.title, o.description FROM `offers_variations` ov INNER JOIN `offers` o ON ov.offer_id = o.id WHERE o.id = " + offerId + " AND ov.status = 'live' AND o.status = 'live'");

        OfferViewResponse offerViewResponse = new OfferViewResponse();
        OfferDetails offerDetails = new OfferDetails();
        offerDetails.variationsDetails = new ArrayList<>();

        HashMap<Integer, String> variationsPictures = new HashMap<>();
        HashMap<Integer, String> variationsColors = new HashMap<>();

        while (resultSet.next()) {
            if(resultSet.getInt("ov.id") == variationId) {
                offerViewResponse.variationId = resultSet.getInt("ov.id");
                offerViewResponse.title = resultSet.getString("o.title");
                offerViewResponse.description = resultSet.getString("o.description");
                offerViewResponse.picture = resultSet.getString("ov.picture");
                offerViewResponse.color = resultSet.getString("ov.color");
            }

            variationsPictures.put(resultSet.getInt("ov.id"), resultSet.getString("ov.picture"));
            variationsColors.put(resultSet.getInt("ov.id"), resultSet.getString("ov.color"));

        }

        offerViewResponse.variationsPictures = variationsPictures;
        offerViewResponse.variationsColors = variationsColors;

        OffersStockDetailsModel offersStockDetailsModel = new OffersStockDetailsModel();
        offerViewResponse.offerStockDetails = offersStockDetailsModel.getOfferStockDetailsByVariationId(variationId);

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

            if(offerDetails.variationsDetails.size() == 0) {
                throw new IllegalArgumentException();
            }

            // Add OfferVariations
            OfferVariationModel offerVariationModel = new OfferVariationModel();
            for(VariationDetails variationDetails : offerDetails.variationsDetails) {
                int newVarationId = offerVariationModel.addOfferVariation(variationDetails.offerVariation);

                if(newVarationId > 0) {
                    variationDetails.setNewVariationId(newVarationId);
                }else {
                    throw new IllegalArgumentException();
                }

                // Add OfferStockDetail
                OffersStockDetailsModel offersStockDetailsModel = new OffersStockDetailsModel();
                for (OfferStockDetail offerStockDetail : variationDetails.offerStockDetails) {
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
