package com.thaer.jj.core;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 16, 2016.
 */

@ApplicationPath("/")
public class JerseyApp extends ResourceConfig {

    public JerseyApp() {
        packages("com.thaer.jj");
        register(MultiPartFeature.class);
    }

}
