package com.thaer.jj.controller;

import com.thaer.jj.exceptions.UnAuthorizedException;
import com.thaer.jj.core.config.Config;
import com.thaer.jj.model.UserModel;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Thaer AlDwaik on February 11, 2016.
 */

@Path("userAuth")
public class UserAuthController extends AbstractController {

    private String key;

    public UserAuthController() throws IOException {
        key = Config.getConfig("jwt.secret");
    }

    @POST @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {

        try {
            UserModel userModel = new UserModel();

            String username = userModel.getUsernameByAuth(email.toLowerCase(), password);

            Date expirationDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(expirationDate);
            cal.add(Calendar.HOUR_OF_DAY, 1);
            expirationDate = cal.getTime();

            String jwtAuthorization = Jwts.builder()
                    .setIssuer(request.getRemoteAddr())
                    .setSubject(username)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact();

            String[] jwtAuthorizationParts = jwtAuthorization.split("\\.");

            jwtAuthorization = jwtAuthorizationParts[1] + "." + jwtAuthorizationParts[2];

            return Response.accepted().header("Authorization", jwtAuthorization).build();

        }catch (UnAuthorizedException e) {
            return Response.status(401).build();
        }catch (Exception e) {
            return Response.status(500).build();
        }

    }

    @GET @Path("/isLogin")
    public Response isLogin(@HeaderParam("Authorization") String authorization) {

        if(getUser() == null) {
            return Response.status(401).build();
        }else {
            return Response.ok().build();
        }

//        if(authorization == null || authorization.isEmpty()) {
//            return Response.status(401).build();
//        }
//
//        try {
//            authorization = "eyJhbGciOiJIUzUxMiJ9." + authorization;
//            Jwts.parser()
//                    .requireIssuer(request.getRemoteAddr())
//                    .setSigningKey(key)
//                    .parseClaimsJws(authorization);
//
//            return Response.ok().build();
//        }catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException e) {
//            return Response.status(401).build();
//        }catch (Exception e) {
//            return Response.status(500).build();
//        }

    }

}
