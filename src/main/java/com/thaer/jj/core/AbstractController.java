package com.thaer.jj.core;

import com.google.gson.Gson;
import com.thaer.jj.core.config.Config;

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
    protected String authorization;

    /**
     *
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @PostConstruct
    public void init() throws SQLException {

        if(!securityCheckRequist()) {
            if(!"DEV".equals(Config.getConfig("ENV"))) {
                System.exit(-1);
            }
        }

        run();

    }

    protected abstract void run();

    private boolean securityCheckRequist() {
        if(!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return false;
        }

        if(!request.getHeader("Origin").matches("http(|s):\\/\\/(|(\\D{1,10}\\.))eproject\\.com")) {
            return false;
        }

        return true;
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
