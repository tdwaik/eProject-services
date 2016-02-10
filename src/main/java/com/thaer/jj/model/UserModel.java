package com.thaer.jj.model;

import com.thaer.jj.core.Dependencies;
import com.thaer.jj.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by thaer on 2/10/16.
 */
public class UserModel {

    public User getUserById(int id) throws SQLException, ClassNotFoundException {
        Statement query = Dependencies.Mysql().createStatement();

        ResultSet rs = query.executeQuery("SELECT id, username FROM users WHERE id = " + id);

        User user = new User();
        while(rs.next()) {
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
        }

        return user;
    }

    public int addUser(String username, String email, String firstname, String lastname, String phoneNumber) throws SQLException, ClassNotFoundException {

        Statement query = Dependencies.Mysql().createStatement();
        return query.executeUpdate(
                "INSERT INTO users " +
                        "(username, email, firstname, lastname, phone_number) " +
                        "VALUES " +
                        "('" + username + "', '" + email + "', '" + firstname + "', '" + lastname + "', '" + phoneNumber + "')");
    }
}
