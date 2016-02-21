package com.thaer.jj.core;

import com.google.gson.Gson;

/**
 * Created by Thaer AlDwaik on February 10, 2016.
 */
public class App extends Dependencies {

    public static String PATH = System.getProperty("user.dir") + "/../webapps/ROOT";

    public Response response() {
        return new Response();
    }

    public String toJson(Object data) {

        Gson gson = new Gson();
        return gson.toJson(data);

    }

}
