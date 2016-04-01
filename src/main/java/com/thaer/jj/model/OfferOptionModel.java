package com.thaer.jj.model;

import com.thaer.jj.entities.OfferOption;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 21, 2016.
 */
public class OfferOptionModel extends AbstractModel {
    public OfferOptionModel() throws SQLException {
    }

    public int addOfferOption(OfferOption offerOption) throws SQLException, IllegalArgumentException {
        String query = "INSERT INTO `offers_options` (`offer_id`, `color`, `picture`, `status`) VALUES (?, ?, ?, ?)";
        preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, offerOption.getOfferId());
        preparedStatement.setString(2, offerOption.getColor());
        preparedStatement.setString(3, offerOption.getPicture());
        preparedStatement.setString(4, offerOption.getStatus());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }else {
            throw new IllegalArgumentException();
        }
    }
}
