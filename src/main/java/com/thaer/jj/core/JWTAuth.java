package com.thaer.jj.core;

import com.thaer.jj.core.config.Config;
import com.thaer.jj.exceptions.UnAuthorizedException;
import com.thaer.jj.model.UserModel;
import io.jsonwebtoken.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016.
 */

public class JWTAuth {

    private String secretKey = null;

    private UserModel userModel;

    private String authUsername;

    public JWTAuth() throws SQLException, IOException, ClassNotFoundException {
        userModel = new UserModel();
    }

    public String getAuthUsername() {
        return authUsername;
    }

    private String generateAuth(String email, String password, String remoteAddr) throws UnAuthorizedException, SQLException, ClassNotFoundException {

        if(secretKey != null && secretKey.length() >= 10) {
            throw new IllegalArgumentException();
        }

        String username = userModel.getUsernameByAuth(email.toLowerCase(), password);

        Date expirationDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(expirationDate);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        expirationDate = cal.getTime();

        String jwtAuthorization = Jwts.builder()
                .setIssuer(remoteAddr)
                .setSubject(username)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        String[] jwtAuthorizationParts = jwtAuthorization.split("\\.");

        jwtAuthorization = jwtAuthorizationParts[1] + "." + jwtAuthorizationParts[2];

        return jwtAuthorization;

    }

    private boolean isAuth(String authorization, String remoteAddr) throws IOException {
        if(authorization == null || authorization.isEmpty()) {
            return false;
        }

        try {
            authorization = "eyJhbGciOiJIUzUxMiJ9." + authorization;
            String JWTUserName = Jwts.parser()
                    .requireIssuer(remoteAddr)
                    .setSigningKey(secretKey)
                    .parseClaimsJws(authorization)
                    .getBody()
                    .getSubject();

            authUsername = JWTUserName;

            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException e) {
            return false;
        }
    }

    public String generateBackofficeUserAuth(String email, String password, String remoteAddr) throws IOException, ClassNotFoundException, UnAuthorizedException, SQLException {
        secretKey = Config.getConfig("jwt.secret");
        return generateAuth(email, password, remoteAddr);
    }

    public String generateUserAuth(String email, String password, String remoteAddr) throws UnAuthorizedException, IOException, SQLException, ClassNotFoundException {
        secretKey = Config.getConfig("backoffice.jwt.secret");
        return generateAuth(email, password, remoteAddr);
    }

    public boolean isUserAuth(String authorization, String remoteAddr) throws IOException {
        secretKey = Config.getConfig("jwt.secret");
        return isAuth(authorization, remoteAddr);
    }

    public boolean isBackofficeUserAuth(String authorization, String remoteAddr) throws IOException {
        secretKey = Config.getConfig("backoffice.jwt.secret");
        return isAuth(authorization, remoteAddr);
    }

}
