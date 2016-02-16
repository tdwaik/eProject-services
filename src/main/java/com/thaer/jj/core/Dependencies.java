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
//        if(mysql == null) {
//            MySql mySql = new MySql();
//            mysql = mySql.setDatabaseName("jj").connect();
//        }

        if(mysql == null) {
            MySql mySql = new MySql();
            mysql = mySql
                    .setHost(Config.getConfig("mysql.host"))
                    .setPort(Integer.parseInt(Config.getConfig("mysql.port")))
                    .setUser(Config.getConfig("mysql.user"))
                    .setPassword(Config.getConfig("mysql.password"))
                    .setDatabaseName(Config.getConfig("mysql.database_name"))
                    .connect();
        }


        return mysql;
    }

}
