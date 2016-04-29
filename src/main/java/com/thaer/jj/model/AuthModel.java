package com.thaer.jj.model;

import com.thaer.jj.core.lib.Validator;
import com.thaer.jj.core.utils.Crypt;
import com.thaer.jj.exceptions.UnAuthorizedException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since April 23, 2016.
 */
public class AuthModel extends AbstractModel {

    public AuthModel() throws SQLException {
    }

    public int getUserIdByAuth(String email, String password) throws UnAuthorizedException, SQLException, IllegalArgumentException {
        return getAuth(email, password, "buyer");
    }

    public int getSellerIdByAuth(String email, String password) throws UnAuthorizedException, SQLException, IllegalArgumentException {
        return getAuth(email, password, "seller");
    }

    public int getBackOfficeUserIdByAuth(String email, String password) throws UnAuthorizedException, SQLException, IllegalArgumentException {
        return getAuth(email, password, "backOffice");
    }

    private int getAuth(String email, String password, String type) throws UnAuthorizedException, SQLException, IllegalArgumentException {

        if(!Validator.checkEmail(email)) {
            throw new IllegalArgumentException();
        }

        String tableName;

        if(type == "buyer") {
            tableName = "buyers";
        }else if(type == "seller") {
            tableName = "sellers";
        }else if(type == "backOffice") {
            tableName = "backoffice_users";
        }else {
            throw new IllegalArgumentException();
        }

        String query = "SELECT id, password FROM " + tableName + " WHERE email = ?";
        preparedStatement = dbCconnection.prepareStatement(query);
        preparedStatement.setString(1, email);
        
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            if(Crypt.checkPassword(password, resultSet.getString("password"))) {
                return resultSet.getInt("id");
            }else {
                throw new UnAuthorizedException();
            }
        } else {
            throw new UnAuthorizedException();
        }

    }

}
