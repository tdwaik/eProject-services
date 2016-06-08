package com.thaer.jj.model;

import com.thaer.jj.core.utils.Strings;
import com.thaer.jj.entities.Color;
import com.thaer.jj.entities.OfferStock;
import com.thaer.jj.entities.Seller;
import com.thaer.jj.model.responseData.CartOfferResponse;
import com.thaer.jj.model.responseData.OfferViewResponse;
import com.thaer.jj.model.responseData.LastOffersResponse;
import com.thaer.jj.model.sets.OfferDetails;
import com.thaer.jj.model.sets.VariationDetails;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Mar 11, 2016.
 */
public class OfferModel extends AbstractModel {

    public OfferModel() throws SQLException {
    }

    public ArrayList<LastOffersResponse> getLastOffers() throws SQLException {
        ResultSet resultSet = executeQuery("SELECT o.id, ov.id, ov.picture, o.title FROM `offers_variations` ov INNER JOIN `offers` o ON ov.offer_id = o.id WHERE ov.status = 'live' AND o.status = 'live' GROUP BY ov.offer_id ORDER BY ov.offer_id DESC LIMIT 20");

        ArrayList<LastOffersResponse> ResponseList = new ArrayList<>();
        ArrayList<Integer> variationsIds = new ArrayList<>();
        LastOffersResponse lastOffersResponse;

        while (resultSet.next()) {
            lastOffersResponse = new LastOffersResponse();

            lastOffersResponse.offerId = resultSet.getInt("o.id");
            lastOffersResponse.title = resultSet.getString("o.title");
            lastOffersResponse.variationId = resultSet.getInt("ov.id");
            lastOffersResponse.picture = resultSet.getString("ov.picture");

            variationsIds.add(lastOffersResponse.variationId);
            ResponseList.add(lastOffersResponse);
        }

        String variationsIdsStr = Strings.implode(", ", variationsIds);

        resultSet = executeQuery("SELECT variation_id, price FROM offers_stock WHERE variation_id IN (" + variationsIdsStr + ")");

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

        for(LastOffersResponse offersListRes : ResponseList) {
            prices = pricesMap.get(offersListRes.variationId);

            for(BigDecimal price : prices) {
                offersListRes.maxPrice = (offersListRes.maxPrice == null || offersListRes.maxPrice.compareTo(price) == -1) ? price : offersListRes.maxPrice;
                offersListRes.minPrice = (offersListRes.minPrice == null || offersListRes.minPrice.compareTo(price) == 1) ? price : offersListRes.minPrice;
            }
        }

        return ResponseList;
    }

    public OfferViewResponse getOfferDetailsByVariationId(int offerId, int variationId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT ov.id, ov.picture, ov.total_pictures, ov.color_id, o.brand_id, o.category_id, o.title, o.description FROM `offers_variations` ov INNER JOIN `offers` o ON ov.offer_id = o.id WHERE o.id = " + offerId + " AND ov.status = 'live' AND o.status = 'live'");

        OfferViewResponse offerViewResponse = new OfferViewResponse();
        OfferDetails offerDetails = new OfferDetails();
        offerDetails.variationsDetails = new ArrayList<>();

        HashMap<Integer, String> variationsPictures = new HashMap<>();
        HashMap<Integer, String> variationsColors = new HashMap<>();

        ArrayList<Integer> colorsIds = new ArrayList<>();

        while (resultSet.next()) {
            if(resultSet.getInt("ov.id") == variationId) {
                offerViewResponse.categoryId = resultSet.getInt("o.category_id");
                offerViewResponse.variationId = resultSet.getInt("ov.id");
                offerViewResponse.title = resultSet.getString("o.title");
                offerViewResponse.description = resultSet.getString("o.description");
                offerViewResponse.picture = resultSet.getString("ov.picture");
                offerViewResponse.totalPictures =  resultSet.getInt("ov.total_pictures");

                int brandId = resultSet.getInt("o.brand_id");
                BrandsModel brandsModel = new BrandsModel();
                String BrandName = brandsModel.getBrandById(brandId).getName(); // TODO null check
                offerViewResponse.brand = BrandName;
            }

            variationsPictures.put(resultSet.getInt("ov.id"), resultSet.getString("ov.picture"));
            variationsColors.put(resultSet.getInt("ov.id"), resultSet.getString("ov.color_id"));
            colorsIds.add(resultSet.getInt("ov.color_id"));
        }

        ColorsModel colorsModel = new ColorsModel();
        HashMap<Integer, Color> colors = colorsModel.getColorsIn(colorsIds);

        Iterator it = variationsColors.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            int colorId = Integer.parseInt((String) pair.getValue());
            String colorName = colors.get(colorId).getName();
            pair.setValue(colorName);
        }

        offerViewResponse.variationsPictures = variationsPictures;
        offerViewResponse.variationsColors = variationsColors;

        OfferStockModel offerStockModel = new OfferStockModel();
        offerViewResponse.offerStockList = offerStockModel.getOfferStockByVariationId(variationId);

        return offerViewResponse;
    }

    public boolean addOffers(Seller seller, OfferDetails offerDetails) throws SQLException, IllegalArgumentException {

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

                // Add OfferStock
                OfferStockModel offerStockModel = new OfferStockModel();
                for (OfferStock offerStock : variationDetails.offerStockList) {
                    offerStockModel.addStock(offerStock);
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
