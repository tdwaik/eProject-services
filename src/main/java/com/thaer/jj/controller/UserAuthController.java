package com.thaer.jj.controller;

import com.thaer.jj.core.JWTAuth;
import com.thaer.jj.exceptions.UnAuthorizedException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 11, 2016.
 */

@Path("usersAuth")
public class UserAuthController extends MainController {

    @POST @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password, @FormParam("rememberMe") boolean rememberMe) {
        try {
            JWTAuth jwtAuth = new JWTAuth();
            String jwtAuthorization = jwtAuth.generateUserAuth(email, password, request.getRemoteAddr(), rememberMe);

            return Response.accepted().header("Authorization", jwtAuthorization).build();

        }catch (UnAuthorizedException e) {
            return Response.status(401).build();
        }catch (IllegalArgumentException e) {
            return Response.status(400).build();
        }catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

    @GET @Path("/isLogin")
    public Response isLogin() {

        if(isAuthUser()) {
            return Response.ok().build();
        }else {
            return Response.status(401).build();
        }

    }

}
