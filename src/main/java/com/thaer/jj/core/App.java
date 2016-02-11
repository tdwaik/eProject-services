package com.thaer.jj.core;

import com.google.gson.Gson;

/**
 * Created by Thaer AlDwaik on February 10, 2016.
 */
public class App {

    public String toJson(Object src) {
        Gson gson = new Gson();
        return gson.toJson(src);
    }

}
