package com.thaer.jj.model;

import com.thaer.jj.entities.User;
import com.thaer.jj.model.sets.ItemAttributesDetails;
import com.thaer.jj.model.sets.ProductDetails;

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

    public ArrayList<ProductDetails> getProductsList(String order, int from, int to) throws SQLException, ClassNotFoundException, IOException {
        order = order.toUpperCase();
        if(!order.equals("ASC") && !order.equals("DESC")) {
            throw new IllegalArgumentException();
        }

        ResultSet resultSet = executeQuery("SELECT * FROM offers " +
                "INNER JOIN items ON offers.item_id = items.id " +
                "INNER JOIN categories ON items.category_id = categories.id " +
                "order by offers.id " + order + " LIMIT " + from + ", " + to);
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

    public int addProduct(User user, ProductDetails productDetails) throws SQLException {

        try {

//            productDetails.validate();

            int affectedRows = 0;

            // Start transaction
            dbCconnection.setAutoCommit(false);

            // Add Item
            affectedRows = executeUpdate(
                    "INSERT INTO items " +
                            "(title, description, picture, status) " +
                            "VALUES " +
                            "('" + productDetails.item.getTitle() + "', " +
                            "'" + productDetails.item.getDescription() + "', " +
                            "'" + productDetails.item.getPicture() + ", " +
                            "'pending')"
            );

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            int newItemId = generatedKeys.getInt("id");

            // Add Item Attributes
            if(affectedRows == 1 && productDetails.itemAttributesDetails.size() > 0) {

                affectedRows = 0;
                String valuesQuery = "";

                for(ItemAttributesDetails attribute : productDetails.itemAttributesDetails) {
                    if(valuesQuery.length() > 0) {
                        valuesQuery += ", ";
                    }

                    valuesQuery += "('" + newItemId + "', " +
                            "'" + attribute.itemAttributeValue.getAttributeId() + "', " +
                            "'" + attribute.itemAttributeValue.getValue() + "') ";
                }

                affectedRows = executeUpdate("INSERT INTO items_attributes_values (item_id, attribute_id, value) VALUES " + valuesQuery);
            }

            // Add Offer
            if(affectedRows > 0) {
                executeUpdate(
                        "INSERT INTO offers " +
                                "(seller_id, item_id, price, amount, condition, status) " +
                                "VALUES " +
                                "('" + user.getId() + "', " +
                                "'" + newItemId + "', " +
                                "'" + productDetails.offer.getPrice() + "', " +
                                "'" + productDetails.offer.getAmount() + "', " +
                                "'" + productDetails.offer.getCondition() + "', " +
                                "'pending')"
                );
            }

        }catch (SQLException e) {
            dbCconnection.rollback();
        }finally {
            dbCconnection.setAutoCommit(true);
        }

        return 0;

    }

}
