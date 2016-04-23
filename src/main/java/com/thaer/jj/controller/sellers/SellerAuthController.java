package com.thaer.jj.controller.sellers;

import com.thaer.jj.core.AbstractController;
import com.thaer.jj.core.JWTAuth;
import com.thaer.jj.exceptions.UnAuthorizedException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Mar 31, 2016.
 */
@Path("login")
public class SellerAuthController extends AbstractController {

    protected String getENV() {
        return "sellers";
    }

    protected void run() {}

    @POST
    @Path("/")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        try {
            JWTAuth jwtAuth = new JWTAuth();
            String jwtAuthorization = jwtAuth.generateSellerUserAuth(email, password, request.getRemoteAddr());

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

}
