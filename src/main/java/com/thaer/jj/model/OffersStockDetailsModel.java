package com.thaer.jj.model;

import com.thaer.jj.entities.OfferStockDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 21, 2016.
 */
public class OffersStockDetailsModel extends AbstractModel {
    public OffersStockDetailsModel() throws SQLException {
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
