package com.thaer.jj.core;

import com.thaer.jj.core.config.Config;
import com.thaer.jj.core.lib.MySql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Thaer AlDwaik on February 10, 2016.
 */
public class Dependencies {

    private static Connection mysql = null;

    public static Connection Mysql() throws SQLException, ClassNotFoundException, IOException {

        if(mysql == null) {
            MySql mySql = new MySql();
            mysql = mySql
                    .setHost(Config.getConfig("mysql.host").trim())
                    .setPort(Integer.parseInt(Config.getConfig("mysql.port").trim()))
                    .setUser(Config.getConfig("mysql.user").trim())
                    .setPassword(Config.getConfig("mysql.password").trim())
                    .setDatabaseName(Config.getConfig("mysql.database_name").trim())
                    .connect();
        }


        return mysql;
    }

}
