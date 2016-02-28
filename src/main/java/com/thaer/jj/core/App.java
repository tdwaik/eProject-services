package com.thaer.jj.core;

import com.google.gson.Gson;
import com.thaer.jj.core.config.Config;
import com.thaer.jj.entities.User;
import com.thaer.jj.model.UserModel;
import io.jsonwebtoken.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Context;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class App extends Dependencies {

    @HeaderParam("Authorization")
    public String authorization;

    @Context
    public HttpServletRequest request;

    public static String PATH = System.getProperty("user.dir") + "/../webapps/ROOT";

    private User user = null;

    @PostConstruct
    public void init() {
        user = getLoggedinUser();
    }

    public User getUser() {
        return user;
    }

    public Response responseBody() {
        return new Response();
    }

    public String toJson(Object data) {

        Gson gson = new Gson();
        return gson.toJson(data);

    }

    private User getLoggedinUser() {
        if(authorization == null || authorization.isEmpty()) {
            return null;
        }

        try {
            authorization = "eyJhbGciOiJIUzUxMiJ9." + authorization;
            String JWTUserName = Jwts.parser()
                    .requireIssuer(request.getRemoteAddr())
                    .setSigningKey(Config.getConfig("jwt.secret"))
                    .parseClaimsJws(authorization)
                    .getBody()
                    .getSubject();

            UserModel userModel = new UserModel();
            return userModel.getUserByUsername(JWTUserName);
        }catch (Exception e) {
            return null;
        }
    }

}
