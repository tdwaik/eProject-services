package com.thaer.jj.model;

import com.thaer.jj.entities.BackofficeUser;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class BackofficeUserModel extends AbstractModel {

    public BackofficeUserModel() throws SQLException, ClassNotFoundException, IOException {
        super();
    }

    public BackofficeUser getBackofficeUserById(int id) {
        return new BackofficeUser();
    }


}
