package com.thaer.jj.core;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.util.List;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 17, 2016.
 */
@Provider
public class CORSResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {

        List<String> originList = requestContext.getHeaders().get("Origin");

        if(originList != null && originList.get(0).matches("http(|s):\\/\\/(|(\\D{1,10}\\.))eproject\\.com")) {
            MultivaluedMap<String, Object> headers = responseContext.getHeaders();

            headers.add("Access-Control-Allow-Origin", originList.get(0));
            headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "Content-Type, X-Requested-With, accept, Origin, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
            headers.add("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization, Access-Control-Preflight-Maxage");
            headers.add("Access-Control-Preflight-Maxage", "86400");
        }

    }

}
