package com.thaer.jj.model;

import com.thaer.jj.entities.Seller;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since April 23, 2016.
 */
public class SellerModel extends AbstractModel {
    public SellerModel() throws SQLException {
    }

    public Seller getSellerById(int id) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT id, name, status FROM sellers WHERE id = " + id);
        return fillData(resultSet);
    }

    private Seller fillData(ResultSet resultSet) throws SQLException {

        Seller seller = new Seller();

        if(resultSet.next()) {
            seller.setId(resultSet.getInt("id"));
            seller.setName(resultSet.getString("name"));
            seller.setStatus(resultSet.getString("status"));

            return seller;
        }else {
            return null;
        }

    }
}
