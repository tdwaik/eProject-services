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

    public ArrayList<OfferStockDetail> getOfferStockDetailsByOptionId(int offerOptionId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM offers_stock_details WHERE offer_option_id = " + offerOptionId + " ORDER BY price ASC");
        ArrayList<OfferStockDetail> offerStockDetails = new ArrayList<>();
        while(resultSet.next()) {
            offerStockDetails.add(fillData(resultSet));
        }
        return offerStockDetails;
    }

    public HashMap<Integer, ArrayList<OfferStockDetail>> getOfferStockDetailsInOptionIds(String offerOptionIds) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM offers_stock_details WHERE offer_option_id IN (" + offerOptionIds + ")");
        HashMap<Integer, ArrayList<OfferStockDetail>> offerStockDetailMap = new HashMap<>();
        ArrayList<OfferStockDetail> offerStockDetails;
        while(resultSet.next()) {

            int offerOptionId = resultSet.getInt("offer_option_id");

            if(!offerStockDetailMap.containsKey(offerOptionId)) {
                offerStockDetails = new ArrayList<>();
                offerStockDetailMap.put(resultSet.getInt("offer_option_id"), offerStockDetails);
            }

            offerStockDetailMap.get(offerOptionId).add(fillData(resultSet));
        }
        return offerStockDetailMap;
    }

    public HashMap<Integer, ArrayList<OfferStockDetail>> getOfferStockDetailsInOptionIds(ArrayList<Integer> offerOptionIds) throws SQLException {
        String str = Strings.implode(", ", offerOptionIds);
        return getOfferStockDetailsInOptionIds(str);
    }

    public OfferStockDetail fillData(ResultSet resultSet) throws SQLException {
        OfferStockDetail offerStockDetail;
        offerStockDetail = new OfferStockDetail();
        offerStockDetail.setId(resultSet.getInt("id"));
        offerStockDetail.setOfferOptionId(resultSet.getInt("offer_option_id"));
        offerStockDetail.setSizeId(resultSet.getInt("size_id"));
        offerStockDetail.setStockQuantity(resultSet.getInt("stock_quantity"));
        offerStockDetail.setPrice(resultSet.getBigDecimal("price"));
        return offerStockDetail;
    }

    public int addOfferPrice(OfferStockDetail offerStockDetail) throws SQLException, IllegalArgumentException {

        String query = "INSERT INTO `" + OfferStockDetail.tableName + "` (`offer_option_id`, `size_id`, `price`, `stock_quantity`) VALUES (?, ?, ?, ?)";
        preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, offerStockDetail.getOfferOptionId());
        preparedStatement.setInt(2, offerStockDetail.getSizeId());
        preparedStatement.setBigDecimal(3, offerStockDetail.getPrice());
        preparedStatement.setInt(4, offerStockDetail.getStockQuantity());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }else {
            throw new IllegalArgumentException();
        }
    }
}
