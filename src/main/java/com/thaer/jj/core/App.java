package com.thaer.jj.core;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class App extends Dependencies {

    @Context
    public HttpServletRequest request;

    public static String PATH = System.getProperty("user.dir") + "/../webapps/ROOT";

    public Response responseBody() {
        return new Response();
    }

    public String toJson(Object data) {

        Gson gson = new Gson();
        return gson.toJson(data);

    }

}
