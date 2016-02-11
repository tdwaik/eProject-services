package com.thaer.jj.model;

import com.thaer.jj.core.App;
import com.thaer.jj.core.Dependencies;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Thaer AlDwaik on February 10, 2016.
 */
public class AbstractModel extends App {

    private Statement statement;

    public AbstractModel() throws SQLException, ClassNotFoundException {
        statement = Dependencies.Mysql().createStatement();
    }

    public ResultSet executeQuery(String query) throws SQLException, ClassNotFoundException {
        return statement.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        return statement.executeUpdate(query);
    }

}
