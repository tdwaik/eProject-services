package com.thaer.jj.core;

import com.google.gson.Gson;

/**
 * Created by thaer on 2/10/16.
 */
public class App {

    public String toJson(Object src) {
        Gson gson = new Gson();
        return gson.toJson(src);
    }

}
