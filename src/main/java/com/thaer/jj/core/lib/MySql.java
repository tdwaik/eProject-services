package com.thaer.jj.core.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class MySql {

    private String host = "localhost";

    private int port = 3306;

    private String user = "root";

    private String password = "root";

    private  String databaseName =  "";

    private Connection connection;

    public String getHost() {
        return host;
    }

    public MySql setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public MySql setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUser() {
        return user;
    }

    public MySql setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MySql setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public MySql setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public Connection getConnection() {
        return connection;
    }

    public MySql setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public Connection connect() throws SQLException, ClassNotFoundException {
        String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;

        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(connectionUrl, user, password);
    }

}
