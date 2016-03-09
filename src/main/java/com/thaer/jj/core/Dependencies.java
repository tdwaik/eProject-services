package com.thaer.jj.core;

import com.thaer.jj.core.config.Config;
import com.thaer.jj.core.lib.MySql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class Dependencies {

    private static Connection mysql = null;

    public static Connection Mysql() {

        if(mysql == null) {
            MySql mySql = new MySql();
            try {
                mysql = mySql
                        .setHost(Config.getConfig("mysql.host").trim())
                        .setPort(Integer.parseInt(Config.getConfig("mysql.port").trim()))
                        .setUser(Config.getConfig("mysql.user").trim())
                        .setPassword(Config.getConfig("mysql.password").trim())
                        .setDatabaseName(Config.getConfig("mysql.database_name").trim())
                        .connect();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }


        return mysql;
    }

}
