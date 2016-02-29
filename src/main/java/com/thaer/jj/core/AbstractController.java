package com.thaer.jj.core;

import com.thaer.jj.entities.User;
import com.thaer.jj.model.BackofficeUserModel;
import com.thaer.jj.model.UserModel;

import javax.annotation.PostConstruct;
import javax.ws.rs.HeaderParam;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public abstract class AbstractController extends App {

    @HeaderParam("Authorization")
    public String authorization;

    public boolean isAuthUser = false;

    private User authUser = null;

    protected boolean isBackofficeEnv = false;

    /**
     *
     * @return
     */
    public User getAuthUser() {
        return authUser;
    }

    /**
     *
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @PostConstruct
    public void init() throws SQLException, IOException, ClassNotFoundException {
        if(isBackofficeEnv) {
            setAuthBackofficeUser();
        }else {
            setAuthUser();
        }
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void setAuthUser() throws IOException, SQLException, ClassNotFoundException {
        JWTAuth jwtAuth = new JWTAuth();

        if (jwtAuth.isUserAuth(authorization, request.getRemoteAddr())) {
            isAuthUser = true;

            UserModel userModel = new UserModel();
            authUser = userModel.getUserByUsername(jwtAuth.getAuthUsername());
        }

    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void setAuthBackofficeUser() throws IOException, SQLException, ClassNotFoundException {
        JWTAuth jwtAuth = new JWTAuth();

        if (jwtAuth.isBackofficeUserAuth(authorization, request.getRemoteAddr())) {
            isAuthUser = true;

            BackofficeUserModel backofficeUserModel = new BackofficeUserModel();
            authUser = backofficeUserModel.getUserByUsername(jwtAuth.getAuthUsername());
        }

    }

}
