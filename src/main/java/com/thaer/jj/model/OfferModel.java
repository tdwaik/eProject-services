package com.thaer.jj.model;

import com.thaer.jj.entities.User;
import com.thaer.jj.model.sets.OfferDetails;

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
                "SELECT * FROM offers_options oo " +
                        "INNER JOIN offers o ON oo.offer_id = o.id " +
                        "INNER JOIN offers_prices op ON op.offer_id = oo.id " +
                        "LIMIT 10");

        return fillOfferDetailsData(resultSet);
    }

    private ArrayList<OfferDetails> fillOfferDetailsData(ResultSet resultSet) throws SQLException {

        ArrayList<OfferDetails> offerDetailsList = new ArrayList<>();
        OfferDetails offerDetails;

        while (resultSet.next()) {
            offerDetails = new OfferDetails();

            offerDetails.offerOption.setTitle(resultSet.getString("oo.title"));
            offerDetails.offerOption.setPicture(resultSet.getString("oo.picture"));

            offerDetailsList.add(offerDetails);
        }

        return offerDetailsList;
    }


    public boolean addOffers(User seller, ArrayList<OfferDetails> offersDetailsList) throws SQLException, IllegalArgumentException {

        dbCconnection.setAutoCommit(false);

        try {

            for (OfferDetails offerDetails : offersDetailsList) {

                // Add offer
                String query = "INSERT INTO `offers` (`id_seller`, `brand_id`, `categoryId`, `description`, `status`) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, seller.getId());
                preparedStatement.setInt(2, offerDetails.offer.getBrandId());
                preparedStatement.setInt(3, offerDetails.offer.getCategoryId());
                preparedStatement.setString(4, offerDetails.offer.getDescription());
                preparedStatement.setString(5, offerDetails.offer.getStatus());
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                int newOfferId;

                if(resultSet.next()) {
                    newOfferId = resultSet.getInt(1);
                }else {
                    throw new IllegalArgumentException();
                }

                // Add OfferOptions
                OfferOptionModel offerOptionModel = new OfferOptionModel();
                offerDetails.offerOption.setOfferId(newOfferId);
                offerOptionModel.addOfferOption(offerDetails.offerOption);

                // Add OfferPrice
                OfferPriceModel offerPriceModel = new OfferPriceModel();
                offerDetails.offerPrice.setOfferOptionId(newOfferId);
                offerPriceModel.addOfferPrice(offerDetails.offerPrice);
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
