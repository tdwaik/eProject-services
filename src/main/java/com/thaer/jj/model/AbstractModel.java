package com.thaer.jj.model;

import com.thaer.jj.core.Dependencies;

import java.sql.*;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public abstract class AbstractModel {

    public Connection dbCconnection;

    public Statement statement;

    public PreparedStatement preparedStatement;

    public AbstractModel() throws SQLException {
        dbCconnection = Dependencies.Mysql();
        statement = dbCconnection.createStatement();
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        PreparedStatement preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        this.preparedStatement = preparedStatement;
        return preparedStatement.executeUpdate();
    }

}
