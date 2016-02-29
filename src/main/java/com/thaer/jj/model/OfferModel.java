package com.thaer.jj.model;

import com.thaer.jj.model.helpers.ItemAttributesDetails;
import com.thaer.jj.model.helpers.ProductDetails;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016
 */
public class OfferModel extends AbstractModel {
    public OfferModel() throws SQLException, ClassNotFoundException, IOException {
    }

    public ArrayList<ProductDetails> getLastProducts() throws SQLException, ClassNotFoundException, IOException {

        ResultSet resultSet = executeQuery("SELECT * FROM offers " +
                "INNER JOIN items ON offers.item_id = items.id " +
                "INNER JOIN categories ON items.category_id = categories.id " +
                "LIMIT 10");
        return fillData(resultSet);

    }

    public ProductDetails getProductDetails(int offerId) throws SQLException, ClassNotFoundException, IOException {

        ResultSet resultSet = executeQuery(
                        "SELECT * FROM offers " +
                        "INNER JOIN items ON offers.item_id = items.id " +
                        "INNER JOIN categories ON items.category_id = categories.id " +
                        "WHERE offers.id = " + offerId
        );

        return fillData(resultSet).get(0);

    }

    public ArrayList<ProductDetails> fillData(ResultSet resultSet) throws SQLException, IOException, ClassNotFoundException {

        ArrayList<ProductDetails> productDetailsList = new ArrayList<>();

        ItemAttributeModel itemAttributeModel = new ItemAttributeModel();

        while(resultSet.next()) {

            ProductDetails productDetails = new ProductDetails();

            productDetails.offer.setId(resultSet.getInt("offers.id"));
            productDetails.offer.setPrice(resultSet.getInt("offers.price"));
            productDetails.offer.setAmount(resultSet.getInt("offers.amount"));
            productDetails.offer.setInsertDate(resultSet.getTimestamp("offers.insert_date"));
            productDetails.offer.setLastUpdate(resultSet.getTimestamp("offers.last_update"));

            productDetails.item.setTitle(resultSet.getString("items.title"));
            productDetails.item.setDescription(resultSet.getString("items.description"));
            productDetails.item.setPicture(resultSet.getString("items.picture"));

            productDetails.category.setId(resultSet.getInt("categories.id"));
            productDetails.category.setIsMain(resultSet.getBoolean("categories.is_main"));
            productDetails.category.setSubOf(resultSet.getInt("categories.sub_of"));
            productDetails.category.setName(resultSet.getString("categories.name"));

            ArrayList<ItemAttributesDetails> itemAttributesDetails = itemAttributeModel.getItemAttributes(resultSet.getInt("items.id"));

            productDetails.itemAttributesDetails = itemAttributesDetails;

            productDetailsList.add(productDetails);
        }

        return productDetailsList;

    }

//    public int addProduct(ProductDetails productDetails) {
//
//        // Add Item
//        executeUpdate(
//                "INSERT INTO users " +
//                        "(username, email, password, firstname, lastname, phone_number) " +
//                        "VALUES " +
//                        "('" + username + "', '" + email + "', '" + hashedPassowrd + "', '" + firstname + "', '" + lastname + "', '" + phoneNumber + "')"
//        );
//
//        // Add Item Attributes
//        executeUpdate(
//                "INSERT INTO users " +
//                        "(username, email, password, firstname, lastname, phone_number) " +
//                        "VALUES " +
//                        "('" + username + "', '" + email + "', '" + hashedPassowrd + "', '" + firstname + "', '" + lastname + "', '" + phoneNumber + "')"
//        );
//
//        // Add Offer
//        executeUpdate(
//                "INSERT INTO users " +
//                        "(username, email, password, firstname, lastname, phone_number) " +
//                        "VALUES " +
//                        "('" + username + "', '" + email + "', '" + hashedPassowrd + "', '" + firstname + "', '" + lastname + "', '" + phoneNumber + "')"
//        );
//
//        // return success
//        return true;
//    }

}
