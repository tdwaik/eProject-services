package com.thaer.jj.core.config;

import com.thaer.jj.core.App;

import java.io.*;
import java.util.Properties;

/**
 * Created by Thaer AlDwaik on February 14, 2016.
 */
public class Config {

    private static InputStream input = null;

    private static Properties prop = null;

    public static String getConfig(String key) throws IOException {

        if(input == null) {
            input = new FileInputStream(App.PATH + "/WEB-INF/classes/config/config.properties");

            // load a properties file
            prop = new Properties();
            prop.load(input);
        }

        return prop.getProperty(key);

    }

}
