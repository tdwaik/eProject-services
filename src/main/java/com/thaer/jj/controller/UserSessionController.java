package com.thaer.jj.controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

/**
 * Created by Thaer AlDwaik on February 11, 2016.
 */

@Path("userSession")
public class UserSessionController extends AbstractController {

    @POST @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@FormParam("email") String email, @FormParam("password") String password) {
        // todo add session

        HashMap<String, Object> result = new HashMap<>();
        result.put("error", false);
        return toJson(result);
    }

    @POST @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public String logout() {

        // todo remove session

        HashMap<String, Object> result = new HashMap<>();
        result.put("error", false);
        return toJson(result);
    }

}
