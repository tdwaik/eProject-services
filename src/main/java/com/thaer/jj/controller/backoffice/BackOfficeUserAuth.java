package com.thaer.jj.controller.backoffice;

import com.thaer.jj.core.JWTAuth;
import com.thaer.jj.exceptions.UnAuthorizedException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 29, 2016.
 */
@Path("backoffice/auth")
public class BackOfficeUserAuth extends BackOfficeController {

    @Override
    protected boolean requireAUth() {
        return false;
    }

    @POST
    @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        try {
            JWTAuth jwtAuth = new JWTAuth();
            String jwtAuthorization = jwtAuth.generateBackOfficeUserAuth(email, password, request.getRemoteAddr());

            return Response.accepted().header("Authorization", jwtAuthorization).build();

        }catch (UnAuthorizedException e) {
            return Response.status(401).build();
        }catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

    @GET
    @Path("/isLogin")
    public Response isLogin() {

        if(getAuthBackofficeUser() != null) {
            return Response.ok().build();
        }else {
            return Response.status(401).build();
        }

    }

}
