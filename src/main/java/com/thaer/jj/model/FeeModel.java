package com.thaer.jj.model;

import com.thaer.jj.entities.Fee;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since June 11, 2016.
 */
public class FeeModel extends AddressModel {
    public FeeModel() throws SQLException {
    }

    public Fee getSellerFee(int sellerId) throws SQLException {
        if(sellerId < 1) {
            throw new IllegalArgumentException();
        }

        String query = "SELECT * FROM fees WHERE seller_id = ?";
        preparedStatement = dbCconnection.prepareStatement(query);
        preparedStatement.setInt(1, sellerId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            Fee fee = new Fee();
            fee.setId(resultSet.getInt("id"));
            fee.setSellerId(resultSet.getInt("seller_id"));
            fee.setCategoryId(resultSet.getInt("category_id"));
            fee.setPercentage(resultSet.getFloat("percentage"));

            return fee;
        }else {
            return null;
        }

    }
}
