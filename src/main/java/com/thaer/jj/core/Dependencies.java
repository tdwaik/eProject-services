package com.thaer.jj.core;

import com.google.gson.JsonObject;
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

//    private Config config;
//
//    public Dependencies() {
//        config = new Config();
//    }

    public static Connection Mysql() throws SQLException, ClassNotFoundException, IOException {
        if(mysql == null) {
            MySql mySql = new MySql();
            mysql = mySql.setDatabaseName("jj").connect();
        }

//        if(mysql == null) {
//
//            JsonObject mysqlConfig = config.getConfig("mysql");
//
//            MySql mySql = new MySql();
//            mysql = mySql
//                    .setHost(mysqlConfig.get("host").toString())
//                    .setPort(Integer.parseInt(mysqlConfig.get("port").toString()))
//                    .setUser(mysqlConfig.get("user").toString())
//                    .setPassword(mysqlConfig.get("password").toString())
//                    .setDatabaseName(mysqlConfig.get("database_name").toString())
//                    .connect();
//        }


        return mysql;
    }

}
