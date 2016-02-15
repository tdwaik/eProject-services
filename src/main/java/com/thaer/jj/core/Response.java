package com.thaer.jj.core;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Thaer AlDwaik on February 14, 2016.
 */
public class Response {

    private Boolean error = null;

    private Object message = null;

    private Object result = null;

    public Boolean getError() {
        return error;
    }

    public Response error(Boolean error) {
        this.error = error;
        return this;
    }

    public Object getMessage() {
        return message;
    }

    public Response message(Object message) {
        this.message = message;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public Response result(Object result) {
        this.result = result;
        return this;
    }

    public String toJson() {

        HashMap<String, Object> jsonMap = new HashMap<>();

        if(error != null) {
            jsonMap.put("error", error);
        }

        if(result != null) {
            jsonMap.put("result", result);
        }

        if(message != null) {
            jsonMap.put("message", message);
        }

        Gson gson = new Gson();
        return gson.toJson(jsonMap);

    }

}
