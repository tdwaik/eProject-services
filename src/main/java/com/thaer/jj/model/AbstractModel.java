package com.thaer.jj.model;

import com.thaer.jj.core.Dependencies;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public abstract class AbstractModel {

    public Connection dbCconnection;

    public Statement statement;

    public AbstractModel() throws SQLException, ClassNotFoundException, IOException {
        dbCconnection = Dependencies.Mysql();
        statement = dbCconnection.createStatement();
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        return statement.executeUpdate(query);
    }

}
