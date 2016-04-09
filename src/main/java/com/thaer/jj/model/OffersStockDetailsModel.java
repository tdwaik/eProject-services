package com.thaer.jj.model;

import com.thaer.jj.core.utils.Strings;
import com.thaer.jj.entities.OfferStockDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 21, 2016.
 */
public class OffersStockDetailsModel extends AbstractModel {
    public OffersStockDetailsModel() throws SQLException {
    }

    public ArrayList<OfferStockDetail> getOfferStockDetailsByVariationId(int variationId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM offers_stock_details WHERE variation_id = " + variationId + " ORDER BY price ASC");
        ArrayList<OfferStockDetail> offerStockDetails = new ArrayList<>();
        while(resultSet.next()) {
            offerStockDetails.add(fillData(resultSet));
        }
        return offerStockDetails;
    }

    public HashMap<Integer, ArrayList<OfferStockDetail>> getOfferStockDetailsInVariationIds(String variationIds) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM offers_stock_details WHERE variation_id IN (" + variationIds + ")");
        HashMap<Integer, ArrayList<OfferStockDetail>> offerStockDetailMap = new HashMap<>();
        ArrayList<OfferStockDetail> offerStockDetails;
        while(resultSet.next()) {

            int variationId = resultSet.getInt("variation_id");

            if(!offerStockDetailMap.containsKey(variationId)) {
                offerStockDetails = new ArrayList<>();
                offerStockDetailMap.put(resultSet.getInt("variation_id"), offerStockDetails);
            }

            offerStockDetailMap.get(variationId).add(fillData(resultSet));
        }
        return offerStockDetailMap;
    }

    public HashMap<Integer, ArrayList<OfferStockDetail>> getOfferStockDetailsInVariationIds(ArrayList<Integer> variationIds) throws SQLException {
        String str = Strings.implode(", ", variationIds);
        return getOfferStockDetailsInVariationIds(str);
    }

    public OfferStockDetail fillData(ResultSet resultSet) throws SQLException {
        OfferStockDetail offerStockDetail;
        offerStockDetail = new OfferStockDetail();
        offerStockDetail.setId(resultSet.getInt("id"));
        offerStockDetail.setVariationId(resultSet.getInt("variation_id"));
        offerStockDetail.setSizeId(resultSet.getInt("size_id"));
        offerStockDetail.setStockQuantity(resultSet.getInt("stock_quantity"));
        offerStockDetail.setPrice(resultSet.getBigDecimal("price"));
        return offerStockDetail;
    }

    public int addStockDetail(OfferStockDetail offerStockDetail) throws SQLException, IllegalArgumentException {

        String query = "INSERT INTO `" + OfferStockDetail.tableName + "` (`variation_id`, `size_id`, `price`, `stock_quantity`, `sku`) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, offerStockDetail.getVariationId());
        preparedStatement.setInt(2, offerStockDetail.getSizeId());
        preparedStatement.setBigDecimal(3, offerStockDetail.getPrice());
        preparedStatement.setInt(4, offerStockDetail.getStockQuantity());
        preparedStatement.setString(5, offerStockDetail.getSku());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }else {
            throw new IllegalArgumentException();
        }
    }
}
