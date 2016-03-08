package com.thaer.jj.model;

import com.thaer.jj.entities.User;
import com.thaer.jj.model.sets.ItemAttributesDetails;
import com.thaer.jj.model.sets.ProductDetails;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 07, 2016.
 */
public class ProductModel extends AbstractModel {

    public ProductModel() throws SQLException {
    }

    public boolean addProduct(User user, ProductDetails productDetails) throws SQLException {

        if(user == null) {
            throw new IllegalArgumentException();
        }

        try {

//            productDetails.validate();

            int affectedRows = 0;

            // Start transaction
            dbCconnection.setAutoCommit(false);

            // Add Item
            ItemModel itemModel = new ItemModel();
            affectedRows = itemModel.addItem(productDetails.item);

            ResultSet generatedKeys = itemModel.preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            int newItemId = generatedKeys.getInt(1);

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

                productDetails.offer.setItemId(newItemId);

                OfferModel offerModel = new OfferModel();
                affectedRows = offerModel.addOffer(user, productDetails.offer);
            }

            if(affectedRows == 1) {
                dbCconnection.commit();
                return true;
            }else {
                return false;
            }

        }catch (SQLException e) {
            dbCconnection.rollback();
            dbCconnection.setAutoCommit(true);
            throw e;
        }

    }

}
