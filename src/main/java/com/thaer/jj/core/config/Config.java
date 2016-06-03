package com.thaer.jj.core.config;

import com.thaer.jj.core.App;

import java.io.*;
import java.util.Properties;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 14, 2016.
 */
public class Config {

    private static InputStream input = null;

    private static Properties prop = null;

    public static String getConfig(String key) {

        if(input == null) {
            try {
                input = new FileInputStream(App.PATH + "/WEB-INF/classes/config/config.properties");

                // load a properties file
                prop = new Properties();
                prop.load(input);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return prop.getProperty(key);

    }

}
