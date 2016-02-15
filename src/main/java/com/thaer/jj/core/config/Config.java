package com.thaer.jj.core.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Thaer AlDwaik on February 14, 2016.
 */
public class Config {

    @Context
    ServletContext servletContext;

    private String configFile = "/WEB-INF/config.inc";

    private JsonObject jsonObjectConfig = null;

    public JsonObject getConfig(String key) throws IOException {

        if(jsonObjectConfig == null) {

            // read config file
            String fullPath = servletContext.getRealPath(configFile);
            File file = new File(fullPath);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String jsonConfigText = new String(data, "UTF-8");

            jsonObjectConfig = new Gson().fromJson(jsonConfigText, JsonObject.class);
        }

        return jsonObjectConfig.getAsJsonObject(key);

    }

}
