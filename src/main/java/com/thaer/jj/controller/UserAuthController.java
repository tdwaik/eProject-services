package com.thaer.jj.controller;

import com.thaer.jj.model.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.sql.SQLException;

/**
 * Created by Thaer AlDwaik on February 11, 2016.
 */

@Path("userAuth")
public class UserAuthController extends AbstractController {

    @Context
    private HttpServletRequest request;

    @POST @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) throws SQLException, ClassNotFoundException {


        try {
            UserModel userModel = new UserModel();

            if(userModel.checkUserPasswordByUserEmail(email.toLowerCase(), password)) {
                String s = Jwts.builder().setSubject(request.getRemoteAddr()).signWith(SignatureAlgorithm.HS512, "fuck").compact();
                return Response.accepted().header("Authorization", s).cookie(new NewCookie("with me", "... fuck me")).build();

            }else {
                return Response.status(403).build();
            }

        } catch (Exception e) {
            return Response.status(500).build();
        }

    }

    @GET @Path("/isLogin")
    public Response isLogin(@HeaderParam("Authorization") String authorization) {

        try {
            Jwts.parser().setSigningKey("fuck").parseClaimsJws(authorization).getBody().getSubject().equals(request.getRemoteAddr());
            return Response.ok().build();
        }catch (SignatureException e) {
            return Response.status(403).build();
        }

    }

}
