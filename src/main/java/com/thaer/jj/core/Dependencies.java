package com.thaer.jj.core;

import com.thaer.jj.core.lib.MySql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by thaer on 2/10/16.
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
