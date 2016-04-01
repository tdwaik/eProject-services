package com.thaer.jj.model;

import com.thaer.jj.entities.OfferOption;
import com.thaer.jj.entities.OfferStockDetail;
import com.thaer.jj.entities.User;
import com.thaer.jj.model.sets.OfferDetails;
import com.thaer.jj.model.sets.OfferOptionDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Mar 11, 2016.
 */
public class OfferModel extends AbstractModel {

    public OfferModel() throws SQLException {
    }

    public ArrayList<OfferDetails> getOfferDetailList() throws SQLException {
        ResultSet resultSet = executeQuery(
                "SELECT * FROM `offers_options` oo " +
                        "INNER JOIN `offers` o ON oo.offer_id = o.id " +
                        "INNER JOIN `" + OfferStockDetail.tableName + "` osd ON osd.offer_option_id = oo.id " +
                        "LIMIT 60");

        return fillOfferDetailsData(resultSet);
    }

    private ArrayList<OfferDetails> fillOfferDetailsData(ResultSet resultSet) throws SQLException {

        ArrayList<OfferDetails> offerDetailsList = new ArrayList<>();
        OfferDetails offerDetails = new OfferDetails();
        OfferOption offerOption;

        while (resultSet.next()) {
            offerOption = new OfferOption();

            offerDetails.offer.setTitle(resultSet.getString("o.title"));
            offerOption.setPicture(resultSet.getString("oo.picture"));

            ArrayList<OfferOptionDetail> offerOptionDetails = new ArrayList<>();
            offerDetails.offerOptionDetails = offerOptionDetails;
            offerDetailsList.add(offerDetails);
        }

        return offerDetailsList;
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

            if(offerDetails.offerOptionDetails.size() == 0) {
                throw new IllegalArgumentException();
            }

            // Add OfferOptions
            OfferOptionModel offerOptionModel = new OfferOptionModel();
            for(OfferOptionDetail offerOptionDetail : offerDetails.offerOptionDetails) {
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
