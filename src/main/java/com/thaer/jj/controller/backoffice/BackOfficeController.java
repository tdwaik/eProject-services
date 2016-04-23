package com.thaer.jj.controller.backoffice;

import com.thaer.jj.core.AbstractController;
import com.thaer.jj.core.JWTAuth;
import com.thaer.jj.entities.BackofficeUser;
import com.thaer.jj.model.BackofficeUserModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016.
 */
public abstract class BackOfficeController extends AbstractController {

    private BackofficeUser authBackofficeUser = null;

    public BackofficeUser getAuthBackofficeUser() {
        return authBackofficeUser;
    }

    protected void run() {
        try {
            setAuthBackofficeUser();
        }catch (SQLException e) {
            System.exit(-1);
        }

        if (authBackofficeUser == null) {
            System.exit(1);
        }

    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void setAuthBackofficeUser() throws SQLException {
        JWTAuth jwtAuth = new JWTAuth();

        if (jwtAuth.isBackofficeUserAuth(authorization, request.getRemoteAddr())) {
            BackofficeUserModel backofficeUserModel = new BackofficeUserModel();
            authBackofficeUser = backofficeUserModel.getBackofficeUserById(jwtAuth.getAuthUserId());
        }

    }

}
