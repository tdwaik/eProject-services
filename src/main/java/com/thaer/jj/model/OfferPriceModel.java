package com.thaer.jj.model;

import com.thaer.jj.entities.OfferPrice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 21, 2016.
 */
public class OfferPriceModel extends AbstractModel {
    public OfferPriceModel() throws SQLException {
    }

    public int addOfferPrice(OfferPrice offerPrice) throws SQLException, IllegalArgumentException {

        String query = "INSERT INTO offersPrices (`OfferId`, `sizeId`, `price`, `stockQuantity`) VALUES (?, ?, ?, ?)";
        preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, offerPrice.getOfferOptionId());
        preparedStatement.setInt(2, offerPrice.getSizeId());
        preparedStatement.setBigDecimal(3, offerPrice.getPrice());
        preparedStatement.setInt(4, offerPrice.getStockQuantity());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }else {
            throw new IllegalArgumentException();
        }
    }
}
