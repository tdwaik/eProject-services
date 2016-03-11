package com.thaer.jj.model;

import com.thaer.jj.model.sets.OfferDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
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
                "SELECT * FROM offers o " +
                        "INNER JOIN offers_options oo ON oo.offer_id = o.id " +
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

}
