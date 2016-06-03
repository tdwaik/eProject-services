package com.thaer.jj.model;

import com.thaer.jj.entities.Cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since June 3, 2016.
 */
public class CartModel extends AddressModel {
    public CartModel() throws SQLException {
    }

    /**
     *
     * @param buyerId
     * @param offerStockId
     * @param quantity
     * @return
     * @throws SQLException
     */
    public int addToCart(int buyerId, int offerStockId, int quantity) throws SQLException {

        if(quantity < 1 || quantity > 100) {
            throw new IllegalArgumentException();
        }

        // Validate offer variation
        OfferStockModel offerStockModel = new OfferStockModel();
        if(!offerStockModel.isStockExists(offerStockId)) {
            throw new IllegalArgumentException();
        }

        // if exits add increment quantity
        String query = "SELECT id FROM cart WHERE buyer_id = ? AND offer_stock_id = ?";
        PreparedStatement preparedStatement = dbCconnection.prepareStatement(query);
        preparedStatement.setInt(1, buyerId);
        preparedStatement.setInt(2, offerStockId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            // increment quantity
            int cartId = resultSet.getInt("id");
            return incrementQuantity(cartId, quantity);

        }else {
            // add new item to cart
            query = "INSERT INTO cart (buyer_id, offer_stock_id, quantity) VALUES (?, ?, ?)";
            preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, buyerId);
            preparedStatement.setInt(2, offerStockId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }else {
                return 0;
            }

        }

    }

    /**
     *
     * @param cartId
     * @param quantity
     * @return
     * @throws SQLException
     */
    public int incrementQuantity(int cartId, int quantity) throws SQLException {

        if(quantity < 1) {
            throw new IllegalArgumentException();
        }

        String query = "UPDATE cart SET quantity = ? WHERE id = ?";
        PreparedStatement preparedStatement = dbCconnection.prepareStatement(query);
        preparedStatement.setInt(1, quantity);
        preparedStatement.setInt(2, cartId);

        return preparedStatement.executeUpdate();

    }

    /**
     *
     * @param buyerId
     * @return
     * @throws SQLException
     */
    public ArrayList<Cart> getCartByBuyerId(int buyerId) throws SQLException {
        String query = "SELECT * FROM cart WHERE buyer_id = ?";
        PreparedStatement preparedStatement = dbCconnection.prepareStatement(query);
        preparedStatement.setInt(1, buyerId);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Cart> cartOffers = new ArrayList<>();
        Cart cart;
        while (resultSet.next()) {
            cart = new Cart();
            cart.setId(resultSet.getInt("id"));
            cart.setBuyerId(resultSet.getInt("buyer_id"));
            cart.setOfferStockId(resultSet.getInt("offer_stock_id"));
            cart.setInsertDate(resultSet.getTimestamp("insert_date"));
            cart.setLastUpdate(resultSet.getTimestamp("last_update"));
            cartOffers.add(cart);
        }

        return cartOffers;

    }
}
