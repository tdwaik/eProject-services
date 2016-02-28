package com.thaer.jj.model;

import com.thaer.jj.core.App;
import com.thaer.jj.core.Dependencies;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public abstract class AbstractModel extends App {

    private Statement statement;

    public AbstractModel() throws SQLException, ClassNotFoundException, IOException {
        statement = Dependencies.Mysql().createStatement();
    }

    public ResultSet executeQuery(String query) throws SQLException, ClassNotFoundException {
        return statement.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        return statement.executeUpdate(query);
    }

}
