package com.thaer.jj.model;

import com.thaer.jj.model.helper.Product;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Thaer Aldwaik on February 28, 2016
 */
public class OfferModel extends AbstractModel {
    public OfferModel() throws SQLException, ClassNotFoundException, IOException {
    }

    public ArrayList<Product> getLastProducts() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = executeQuery("SELECT * FROM offers INNER JOIN items ON offers.item_id = items.id LIMIT 10");
        return  fillData(resultSet);

    }

    public Product getProductDetails(int offerId) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = executeQuery("SELECT * FROM offers INNER JOIN items ON offers.item_id = items.id WHERE offers.id = " + offerId);
        return  fillData(resultSet).get(0);

    }

    public ArrayList<Product> fillData(ResultSet resultSet) throws SQLException {

        ArrayList<Product> productList = new ArrayList<>();

        while(resultSet.next()) {

            Product product = new Product();

            product.offer.setId(resultSet.getInt("offers.id"));
            product.offer.setPrice(resultSet.getInt("offers.price"));
            product.offer.setAmount(resultSet.getInt("offers.amount"));
            product.offer.setInsertDate(resultSet.getTimestamp("offers.insert_date"));

            product.item.setTitle(resultSet.getString("items.title"));
            product.item.setDescription(resultSet.getString("items.description"));
            product.item.setPicture(resultSet.getString("items.picture"));

            productList.add(product);
        }

        return productList;

    }
}
