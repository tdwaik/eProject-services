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

    public ArrayList<OfferStock> getOfferStockDetailsByVariationId(int variationId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM offers_stock WHERE variation_id = " + variationId + " ORDER BY price ASC");
        ArrayList<OfferStock> offersStock = new ArrayList<>();
        while(resultSet.next()) {
            offersStock.add(fillData(resultSet));
        }
        return offersStock;
    }

    public HashMap<Integer, ArrayList<OfferStock>> getOfferStockDetailsInVariationIds(String variationIds) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM offers_stock WHERE variation_id IN (" + variationIds + ")");
        HashMap<Integer, ArrayList<OfferStock>> offerStockDetailMap = new HashMap<>();
        ArrayList<OfferStock> offersStock;
        while(resultSet.next()) {

            int variationId = resultSet.getInt("variation_id");

            if(!offerStockDetailMap.containsKey(variationId)) {
                offersStock = new ArrayList<>();
                offerStockDetailMap.put(resultSet.getInt("variation_id"), offersStock);
            }

            offerStockDetailMap.get(variationId).add(fillData(resultSet));
        }
        return offerStockDetailMap;
    }

    public HashMap<Integer, ArrayList<OfferStock>> getOfferStockDetailsInVariationIds(ArrayList<Integer> variationIds) throws SQLException {
        String str = Strings.implode(", ", variationIds);
        return getOfferStockDetailsInVariationIds(str);
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

    public int addStockDetail(OfferStock offerStock) throws SQLException, IllegalArgumentException {

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

    public boolean isStockExists(int id) throws SQLException {
        String query = "SELECT count(1) c FROM `offers_stock` WHERE id = ?";
        preparedStatement = dbCconnection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        return resultSet.getInt("c") == 1;
    }
}