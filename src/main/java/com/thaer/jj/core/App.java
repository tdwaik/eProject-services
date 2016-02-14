package com.thaer.jj.core;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

/**
 * Created by Thaer AlDwaik on February 10, 2016.
 */
public class App {

    public String toJson(Boolean error, Object src) {

        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("error", error);
        jsonMap.put("result", src);

        Gson gson = new Gson();
        return gson.toJson(jsonMap);

    }

    public String toJson(Boolean error) {
        return toJson(error, null);
    }

}
