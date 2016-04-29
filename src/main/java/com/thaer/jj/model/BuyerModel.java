package com.thaer.jj.model;

import com.thaer.jj.core.utils.Crypt;
import com.thaer.jj.core.lib.Validator;
import com.thaer.jj.entities.Buyer;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class BuyerModel extends AbstractModel {

    public BuyerModel() throws SQLException {
    }

    public Buyer getUserById(int id) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT id, firstname, lastname, status, phone, gender FROM buyers WHERE id = " + id);
        return fillData(resultSet);
    }

    public Buyer fillData(ResultSet resultSet) throws SQLException {

        Buyer buyer = new Buyer();

        if(resultSet.next()) {
            buyer.setId(resultSet.getInt("id"));
            buyer.setStatus(resultSet.getString("status"));
            buyer.setFirstname(resultSet.getString("firstname"));
            buyer.setLastname(resultSet.getString("lastname"));
            BigDecimal bigDecimal = resultSet.getBigDecimal("phone");
            if(bigDecimal != null) {
                buyer.setPhone(bigDecimal.toBigInteger());
            }
            buyer.setGender(resultSet.getString("gender"));

            return buyer;
        }else {
            return null;
        }

    }

    public int addUser(String email, String password, String firstname, String lastname) throws SQLException {

        if(!validate(email, password, firstname, lastname)) {
            throw new IllegalArgumentException();
        }

        ResultSet resultSet = executeQuery("SELECT COUNT(1) row_count FROM buyers WHERE email = '" + email + "'");
        resultSet.next();
        if(resultSet.getInt("row_count") > 0) {
            return -1;
        }

        String hashedPassowrd = Crypt.hashPasword(password);

        String query = "INSERT INTO `offers` (`email`, `password`, `firstname`, `lastname`, `status`) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, hashedPassowrd);
        preparedStatement.setString(3, firstname);
        preparedStatement.setString(4, lastname);
        preparedStatement.setString(5, "unconfirmed_email");

        resultSet = preparedStatement.getGeneratedKeys();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }else {
            throw new IllegalArgumentException();
        }

    }

    public boolean validate(String email, String password, String firstname, String lastname) {
        if(firstname == null || firstname.length() < 3 || lastname == null || lastname.length() < 3) {
            return false;
        }

        if(password.length() < 6) {
            return false;
        }

        if(!Validator.checkEmail(email)) {
            return false;
        }

        return true;
    }
}
