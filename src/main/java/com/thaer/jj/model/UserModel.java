package com.thaer.jj.model;

import com.thaer.jj.exceptions.UnAuthorizedException;
import com.thaer.jj.core.lib.Validator;
import com.thaer.jj.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class UserModel extends AbstractModel {

    public UserModel() throws SQLException {
    }

    public User getUserById(int id) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT id, firstname, lastname, status, phone_number, is_seller, registration_date FROM users WHERE id = " + id);
        return fillData(resultSet);
    }

    public int getUserIdByAuth(String email, String password) throws UnAuthorizedException, SQLException, IllegalArgumentException {

        if(!Validator.checkEmail(email)) {
            throw new IllegalArgumentException();
        }

        ResultSet resultSet = executeQuery("SELECT id, password FROM users WHERE email = '" + email + "'");

        if (resultSet.next()) {
            if(checkPassword(password, resultSet.getString("password"))) {
                return resultSet.getInt("id");
            }else {
                throw new UnAuthorizedException();
            }
        } else {
            throw new UnAuthorizedException();
        }

    }

    public User fillData(ResultSet resultSet) throws SQLException {

        User user = new User();

        if(resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setStatus(resultSet.getString("status"));
            user.setIsSeller(resultSet.getBoolean("is_seller"));
            user.setFirstname(resultSet.getString("firstname"));
            user.setLastname(resultSet.getString("lastname"));
            user.setRegestrationDate(resultSet.getTimestamp("registration_date"));
            user.setPhoneNumber(resultSet.getString("phone_number"));

            return user;
        }else {
            return null;
        }

    }

    public int addUser(String email, String password, String firstname, String lastname) throws SQLException {

        if(!validate(email, password, firstname, lastname)) {
            throw new IllegalArgumentException();
        }

        ResultSet resultSet = executeQuery("SELECT COUNT(1) row_count FROM users WHERE email = '" + email + "'");
        resultSet.next();
        if(resultSet.getInt("row_count") > 0) {
            return -1;
        }

        String hashedPassowrd = hashPasword(password);

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

    private String hashPasword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(15));
    }

    private boolean checkPassword(String candidate, String hashed) {
        return BCrypt.checkpw(candidate, hashed);
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
