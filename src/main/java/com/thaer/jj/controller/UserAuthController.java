package com.thaer.jj.controller;

import com.thaer.jj.core.config.Config;
import com.thaer.jj.model.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Created by Thaer AlDwaik on February 11, 2016.
 */

@Path("userAuth")
public class UserAuthController extends AbstractController {

    @Context
    private HttpServletRequest request;

    private String key;

    public UserAuthController() throws IOException {
        key = Config.getConfig("jwt.secret");
    }

    @POST @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        Key k = MacProvider.generateKey();

        System.out.println(k);
        try {
            UserModel userModel = new UserModel();

            if(userModel.checkUserPasswordByUserEmail(email.toLowerCase(), password)) {
                String jwt = Jwts.builder().setSubject(request.getRemoteAddr()).setAudience(request.getRemoteAddr()).signWith(SignatureAlgorithm.HS512, key).compact();
                return Response.accepted().header("Authorization", jwt).build();

            }else {
                return Response.status(401).build();
            }

        } catch (Exception e) {
            return Response.status(500).build();
        }

    }

    @GET @Path("/isLogin")
    public Response isLogin(@HeaderParam("Authorization") String authorization) {

        if(authorization == null || authorization.isEmpty()) {
            return Response.status(401).build();
        }

        try {
            Jwts.parser().requireSubject(request.getRemoteAddr()).setSigningKey(key).parseClaimsJws(authorization);
            return Response.ok().build();
        }catch (SignatureException e) {
            return Response.status(401).build();
        }

    }

}
