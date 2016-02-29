package com.thaer.jj.model;

import com.thaer.jj.exceptions.UnAuthorizedException;
import com.thaer.jj.core.lib.Validator;
import com.thaer.jj.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class UserModel extends AbstractModel {

    public UserModel() throws SQLException, ClassNotFoundException, IOException {
        super();
    }

    public User getUserById(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = executeQuery("SELECT id, username, email, is_seller, firstname, lastname, status, phone_number, registration_date FROM users WHERE id = " + id);
        return fillData(resultSet);
    }

    public User getUserByEmail(String email) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = executeQuery("SELECT id, username, email, is_seller, firstname, lastname, status, phone_number, registration_date FROM users WHERE email = '" + email + "'");
        return fillData(resultSet);
    }

    public User getUserByUsername(String username) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = executeQuery("SELECT id, username, email, is_seller, firstname, lastname, status, phone_number, registration_date FROM users WHERE username = '" + username + "'");
        return fillData(resultSet);
    }

    public String getUsernameByAuth(String email, String password) throws UnAuthorizedException, SQLException, ClassNotFoundException {

        if(!Validator.checkEmail(email)) {
            throw new IllegalArgumentException();
        }

        ResultSet resultSet = executeQuery("SELECT password, username FROM users WHERE email = '" + email + "'");

        if (resultSet.next()) {
            if(checkPassword(password, resultSet.getString("password"))) {
                return resultSet.getString("username");
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
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
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

    public int addUser(String username, String email, String password, String firstname, String lastname, String phoneNumber) throws SQLException, ClassNotFoundException {

        if(!validate(username, email, password, firstname, lastname, phoneNumber)) {
            return 0;
        }

        String hashedPassowrd = hashPasword(password);

        return executeUpdate(
                "INSERT INTO users " +
                        "(username, email, password, firstname, lastname, phone_number) " +
                        "VALUES " +
                        "('" + username + "', '" + email + "', '" + hashedPassowrd + "', '" + firstname + "', '" + lastname + "', '" + phoneNumber + "')"
        );

    }

    private String hashPasword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(15));
    }

    private boolean checkPassword(String candidate, String hashed) {
        return BCrypt.checkpw(candidate, hashed);
    }

    public boolean validate(String username, String email, String password, String firstname, String lastname, String phoneNumber) {
        if(username == null || username.length() < 3 || firstname == null || firstname.length() < 3 || lastname == null || lastname.length() < 3) {
            return false;
        }

        if(password.length() < 6) {
            return false;
        }

        if(!Validator.checkEmail(email)) {
            return false;
        }

        if(!Validator.checkPhoneNumber(phoneNumber)) {
            return false;
        }

        return true;
    }
}
