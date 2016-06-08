package com.thaer.jj.model;

import com.thaer.jj.entities.OfferVariation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 21, 2016.
 */
public class OfferVariationModel extends AbstractModel {
    public OfferVariationModel() throws SQLException {
    }

    public int addOfferVariation(OfferVariation offerVariation) throws SQLException, IllegalArgumentException {
        String query = "INSERT INTO `offers_variations` (`offer_id`, `color_id`, `picture`, `total_pictures`, `status`) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, offerVariation.getOfferId());
        preparedStatement.setInt(2, offerVariation.getColor());
        preparedStatement.setString(3, offerVariation.getPicture());
        preparedStatement.setInt(4, offerVariation.getTotalPictures());
        preparedStatement.setString(5, offerVariation.getStatus());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }else {
            throw new IllegalArgumentException();
        }
    }
}
