package com.thaer.jj.model;

import com.thaer.jj.core.utils.Strings;
import com.thaer.jj.entities.OfferStock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 21, 2016.
 */
public class OfferStockModel extends AbstractModel {
    public OfferStockModel() throws SQLException {
    }

    public ArrayList<OfferStock> getOfferStockByVariationId(int variationId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM offers_stock WHERE variation_id = " + variationId + " ORDER BY price ASC");
        ArrayList<OfferStock> offersStock = new ArrayList<>();
        while(resultSet.next()) {
            offersStock.add(fillData(resultSet));
        }
        return offersStock;
    }

    public HashMap<Integer, ArrayList<OfferStock>> getOfferStockInVariationIds(String variationIds) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM offers_stock WHERE variation_id IN (" + variationIds + ")");
        HashMap<Integer, ArrayList<OfferStock>> offerStockMap = new HashMap<>();
        ArrayList<OfferStock> offersStock;
        while(resultSet.next()) {

            int variationId = resultSet.getInt("variation_id");

            if(!offerStockMap.containsKey(variationId)) {
                offersStock = new ArrayList<>();
                offerStockMap.put(resultSet.getInt("variation_id"), offersStock);
            }

            offerStockMap.get(variationId).add(fillData(resultSet));
        }
        return offerStockMap;
    }

    public HashMap<Integer, ArrayList<OfferStock>> getOfferStockInVariationIds(ArrayList<Integer> variationIds) throws SQLException {
        String str = Strings.implode(", ", variationIds);
        return getOfferStockInVariationIds(str);
    }

    public OfferStock fillData(ResultSet resultSet) throws SQLException {
        OfferStock offerStock;
        offerStock = new OfferStock();
        offerStock.setId(resultSet.getInt("id"));
        offerStock.setVariationId(resultSet.getInt("variation_id"));
        offerStock.setSizeId(resultSet.getInt("size_id"));
        offerStock.setStockQuantity(resultSet.getInt("stock_quantity"));
        offerStock.setPrice(resultSet.getBigDecimal("price"));
        return offerStock;
    }

    public int addStock(OfferStock offerStock) throws SQLException, IllegalArgumentException {

        String query = "INSERT INTO `" + OfferStock.tableName + "` (`variation_id`, `size_id`, `price`, `stock_quantity`, `sku`) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, offerStock.getVariationId());
        preparedStatement.setInt(2, offerStock.getSizeId());
        preparedStatement.setBigDecimal(3, offerStock.getPrice());
        preparedStatement.setInt(4, offerStock.getStockQuantity());
        preparedStatement.setString(5, offerStock.getSku());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isStockAvailable(int id) throws SQLException {
        if(id < 1) {
            return false;
        }

        String query = "SELECT stock_quantity FROM `offers_stock` WHERE id = ?";
        preparedStatement = dbCconnection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next() && resultSet.getInt("stock_quantity") > 0;
    }

    public int getSellerIdByStockId(int offerStockId) throws SQLException {
        if(offerStockId < 1) {
            return 0;
        }

        String query = "SELECT seller_id FROM offers INNER JOIN offers_variations ON offers.id = offers_variations.offer_id INNER JOIN `offers_stock` ON offers_stock.variation_id = offers_variations.id WHERE offers_stock.id = ?";
        preparedStatement = dbCconnection.prepareStatement(query);
        preparedStatement.setInt(1, offerStockId);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next()? resultSet.getInt("seller_id") : 0;
    }
}
