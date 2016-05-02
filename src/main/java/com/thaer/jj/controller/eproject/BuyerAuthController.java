package com.thaer.jj.controller.eproject;

import com.thaer.jj.core.JWTAuth;
import com.thaer.jj.exceptions.UnAuthorizedException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 11, 2016.
 */

@Path("buyerAuth")
public class BuyerAuthController extends MainController {

    @POST @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password, @FormParam("rememberMe") boolean rememberMe) {
        try {
            JWTAuth jwtAuth = new JWTAuth();
            String jwtAuthorization = jwtAuth.generateBuyerAuth(email, password, request.getRemoteAddr(), rememberMe);

            return Response.accepted().header("Authorization", jwtAuthorization).build();

        }catch (UnAuthorizedException e) {
            return Response.status(401).build();
        }catch (IllegalArgumentException e) {
            return Response.status(400).build();
        }catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

    @GET @Path("/isLogin")
    public Response isLogin() {

        if(getAuthBuyer() != null) {
            return Response.ok().build();
        }else {
            return Response.status(401).build();
        }

    }

}
