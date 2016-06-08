package com.thaer.jj.model;

import com.thaer.jj.core.utils.Strings;
import com.thaer.jj.entities.Cart;
import com.thaer.jj.exceptions.UnAuthorizedException;
import com.thaer.jj.model.responseData.CartOfferResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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

        if(quantity < 1 || quantity > 100 || offerStockId < 1) {
            throw new IllegalArgumentException();
        }

        // Validate offer stock
        OfferStockModel offerStockModel = new OfferStockModel();
        if(!offerStockModel.isStockAvailable(offerStockId)) {
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
     * @param incrementAmount
     * @return
     * @throws SQLException
     */
    public int incrementQuantity(int cartId, int incrementAmount) throws SQLException {

        if(incrementAmount < 1) {
            throw new IllegalArgumentException();
        }

        String query = "UPDATE cart SET quantity = quantity + ? WHERE id = ?";
        PreparedStatement preparedStatement = dbCconnection.prepareStatement(query);
        preparedStatement.setInt(1, incrementAmount);
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

    /**
     *
     * @param buyerId
     * @return
     * @throws SQLException
     */
    public ArrayList<CartOfferResponse> getCartOffersByBuyerId(int buyerId) throws SQLException {
        String query = "" +
                "SELECT cart.id, cart.quantity, " +
                "o.id, ov.id, o.title, o.status, " +
                "os.price, os.stock_quantity, os.size_id, " +
                "ov.picture, ov.status, " +
                "c.name, " +
                "s.name " +
                "FROM cart " +
                "INNER JOIN offers_stock os ON cart.offer_stock_id = os.id " +
                "INNER JOIN offers_variations ov ON os.variation_id = ov.id " +
                "INNER JOIN offers o ON ov.offer_id = o.id " +
                "INNER JOIN sizes s ON os.size_id = s.id " +
                "INNER JOIN colors c ON ov.color_id = c.id " +
                "WHERE cart.buyer_id = " + buyerId;
        preparedStatement = dbCconnection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<CartOfferResponse> cartOffersResponse = new ArrayList<>();
        CartOfferResponse cartOfferResponse;
        while (resultSet.next()) {
            cartOfferResponse = new CartOfferResponse();
            cartOfferResponse.cartId = resultSet.getInt("cart.id");
            cartOfferResponse.offerId = resultSet.getInt("o.id");
            cartOfferResponse.offerVariationId = resultSet.getInt("ov.id");
            cartOfferResponse.sizeId = resultSet.getInt("os.size_id");
            cartOfferResponse.quantity = resultSet.getInt("cart.quantity");
            cartOfferResponse.title = resultSet.getString("o.title");
            cartOfferResponse.picture = resultSet.getString("ov.picture");
            cartOfferResponse.color = resultSet.getString("c.name");
            cartOfferResponse.size = resultSet.getString("s.name");
            cartOfferResponse.price = resultSet.getBigDecimal("os.price");

            // check if item has stock quantity
            if(resultSet.getString("o.status") == "unavailable" || resultSet.getString("ov.status") == "unavailable") {
                cartOfferResponse.status = "unavailable";
            }else {
                cartOfferResponse.status = resultSet.getInt("os.stock_quantity") > 0 ? "available" : "outOfStock";
            }


            cartOffersResponse.add(cartOfferResponse);
        }

        return cartOffersResponse;
    }

    public int deleteFromCartByCartId(int buyerId, int cartId) throws SQLException, IllegalArgumentException, UnAuthorizedException {

        if(buyerId < 1 || cartId < 1) {
            throw new IllegalArgumentException();
        }

        String query = "SELECT buyer_id FROM cart WHERE id = " + cartId;
        preparedStatement = dbCconnection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next() || resultSet.getInt("buyer_id") != buyerId) {
            throw new UnAuthorizedException();
        }

        query = "DELETE FROM cart WHERE id = " + cartId;
        preparedStatement = dbCconnection.prepareStatement(query);
        return preparedStatement.executeUpdate();

    }
}
