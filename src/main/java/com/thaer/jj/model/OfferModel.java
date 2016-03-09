package com.thaer.jj.model;

import com.thaer.jj.entities.Offer;
import com.thaer.jj.entities.User;
import com.thaer.jj.model.sets.ItemAttributesDetails;
import com.thaer.jj.model.sets.ProductDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016
 */
public class OfferModel extends AbstractModel {
    public OfferModel() throws SQLException {
    }

    public ArrayList<ProductDetails> getProductsList(String order, int from, int to) throws SQLException {
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

    public ProductDetails getProductDetails(int offerId) throws SQLException {

        ResultSet resultSet = executeQuery(
                        "SELECT * FROM offers " +
                        "INNER JOIN items ON offers.item_id = items.id " +
                        "INNER JOIN categories ON items.category_id = categories.id " +
                        "WHERE offers.id = " + offerId
        );

        return fillData(resultSet).get(0);

    }

    public ArrayList<ProductDetails> fillData(ResultSet resultSet) throws SQLException {

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

    public int addOffer(User user, Offer offer) throws SQLException {
        return executeUpdate(
                "INSERT INTO offers " +
                        "(`seller_id`, `item_id`, `price`, `amount`, `condition`, `status`) " +
                        "VALUES " +
                        "(" + user.getId() + ", " +
                        offer.getItemId() + ", " +
                        offer.getPrice() + ", " +
                        offer.getAmount() + ", " +
                        "'" + offer.getCondition() + "', " +
                        "'pending')"
        );
    }

}
