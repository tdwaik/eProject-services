package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016.
 */
public class BackofficeUser {

    private int id;

    private String username;

    private String email;

    private String password;

    private boolean isActive;

    private Timestamp insertDate;

    private Timestamp lastUpdate;
}
