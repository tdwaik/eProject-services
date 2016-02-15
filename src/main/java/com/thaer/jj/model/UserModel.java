package com.thaer.jj.model;

import com.thaer.jj.core.lib.Validator;
import com.thaer.jj.entities.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Thaer AlDwaik on February 10, 2016.
 */
public class UserModel extends AbstractModel {

    public UserModel() throws SQLException, ClassNotFoundException, IOException {
        super();
    }

    public User getUserById(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = executeQuery("SELECT * FROM users WHERE id = " + id);
        return fillData(resultSet);
    }

    public String getUserPasswordByEmail(String email) throws Exception {
        System.out.println("SELECT password FROM users WHERE email = '" + email + "'");
        if(email != null && !email.isEmpty()) {
            ResultSet resultSet = executeQuery("SELECT password FROM users WHERE email = '" + email + "'");

            if (resultSet.next()) {
                return resultSet.getString("password");
            } else {
                throw new Exception("User not found !!");
            }
        }else {
            throw new Exception("Empty Email !!");
        }

    }

    public User fillData(ResultSet resultSet) throws SQLException {

        User user = new User();

        while(resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setStatus(resultSet.getString("status"));
            user.setIsSeller(resultSet.getBoolean("is_seller"));
            user.setFirstname(resultSet.getString("firstname"));
            user.setLastname(resultSet.getString("lastname"));
            user.setRegestrationDate(resultSet.getTimestamp("registration_date"));
            user.setPhoneNumber(resultSet.getString("phone_number"));
        }

        return user;

    }

    public int addUser(String username, String email, String firstname, String lastname, String phoneNumber) throws SQLException, ClassNotFoundException {

        if(!validate(username, email, firstname, lastname, phoneNumber)) {
            return 0;
        }

        return executeUpdate(
                "INSERT INTO users " +
                        "(username, email, firstname, lastname, phone_number) " +
                        "VALUES " +
                        "('" + username + "', '" + email + "', '" + firstname + "', '" + lastname + "', '" + phoneNumber + "')"
        );

    }

    public boolean validate(String username, String email, String firstname, String lastname, String phoneNumber) {
        if(username == null || username.length() < 3 || firstname == null || firstname.length() < 3 || lastname == null || lastname.length() < 3) {
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
