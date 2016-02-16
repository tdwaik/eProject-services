package com.thaer.jj.core;

/**
 * Created by Thaer AlDwaik on February 10, 2016.
 */
public class App extends Dependencies {

    public static String PATH = System.getProperty("user.dir") + "/../webapps/ROOT";

    public Response response() {
        return new Response();
    }

}
