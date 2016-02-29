package com.thaer.jj.backoffice;

import com.thaer.jj.core.JWTAuth;
import com.thaer.jj.exceptions.UnAuthorizedException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 29, 2016.
 */
@Path("backoffice")
public class BackOfficeUserAuth extends BackOfficeController {

    @POST
    @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {

        try {

            JWTAuth jwtAuth = new JWTAuth();

            String jwtAuthorization = jwtAuth.generateBackofficeUserAuth(email, password, request.getRemoteAddr());

            return Response.accepted().header("Authorization", jwtAuthorization).build();

        }catch (UnAuthorizedException e) {
            return Response.status(401).build();

        }catch (IOException | SQLException | ClassNotFoundException e) {
            return Response.status(500).build();
        }

    }

}
