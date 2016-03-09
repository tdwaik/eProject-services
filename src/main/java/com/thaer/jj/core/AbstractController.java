package com.thaer.jj.core;

import com.google.gson.Gson;
import com.thaer.jj.entities.BackofficeUser;
import com.thaer.jj.entities.User;
import com.thaer.jj.model.BackofficeUserModel;
import com.thaer.jj.model.UserModel;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public abstract class AbstractController {

    @Context
    public HttpServletRequest request;

    @HeaderParam("Authorization")
    private String authorization;

    private User authUser = null;

    private BackofficeUser authBackofficeUser = null;

    protected boolean isBackofficeEnv = false;

    public User getAuthUser() {
        return authUser;
    }

    public BackofficeUser getAuthBackofficeUser() {
        return authBackofficeUser;
    }

    public boolean isAuthBackofficeUser() {
        return authBackofficeUser != null;
    }

    public boolean isAuthUser() {
        return authUser != null;
    }

    /**
     *
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @PostConstruct
    public void init() throws SQLException {

        if(!securityCheckRequist()) {
            System.exit(-1);
        }

        if(isBackofficeEnv) {
            //setAuthBackofficeUser();
        }else {
            setAuthUser();
        }
    }

    private boolean securityCheckRequist() {
        if(!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return false;
        }

        if(!request.getHeader("Origin").matches("http(|s):\\/\\/(|(\\D{1,5}\\.))eproject\\.com")) {
            return false;
        }

        return true;
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

    /**
     * add ")]}',\n" to prefix of json to JSON Vulnerability Protection
     * @param data
     * @return
     */
    public static String toJson(Object data) {
        Gson gson = new Gson();
        return ")]}',\n" + gson.toJson(data);
    }

}
