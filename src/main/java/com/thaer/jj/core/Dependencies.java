package com.thaer.jj.core;

import com.thaer.jj.core.lib.MySql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Thaer AlDwaik on February 10, 2016.
 */
public class Dependencies {

    private static Connection mysql = null;

    public static Connection Mysql() throws SQLException, ClassNotFoundException {
        if(mysql == null) {
            MySql mySql = new MySql();
            mysql = mySql.setDatabaseName("jj").connect();
        }

        return mysql;
    }

}
