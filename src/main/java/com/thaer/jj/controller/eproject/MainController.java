package com.thaer.jj.controller.eproject;

import com.thaer.jj.core.AbstractController;
import com.thaer.jj.core.JWTAuth;
import com.thaer.jj.entities.User;
import com.thaer.jj.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public abstract class MainController extends AbstractController {

    private User authUser = null;

    public User getAuthUser() {
        return authUser;
    }

    protected void run() {
        try {
            setAuthUser();
        }catch (SQLException e) {
            System.exit(-1);
        }
    }

    public boolean isAuthUser() {
        return authUser != null;
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void setAuthUser() throws SQLException {
        JWTAuth jwtAuth = new JWTAuth();

        if (jwtAuth.isUserAuth(authorization, request.getRemoteAddr())) {
            UserModel userModel = new UserModel();
            authUser = userModel.getUserById(jwtAuth.getAuthUserId());
        }

    }

}
