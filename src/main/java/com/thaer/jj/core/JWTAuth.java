package com.thaer.jj.core;

import com.thaer.jj.core.config.Config;
import com.thaer.jj.exceptions.UnAuthorizedException;
import com.thaer.jj.model.UserModel;
import io.jsonwebtoken.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016.
 */

public class JWTAuth {

    private String secretKey = null;

    private int authUserId;

    public int getAuthUserId() {
        return authUserId;
    }

    private String generateAuth(String email, String password, String remoteAddr, boolean rememberMe) throws UnAuthorizedException, SQLException, ClassNotFoundException, IOException {

        if(secretKey == null || secretKey.length() < 10) {
            throw new IllegalArgumentException();
        }

        UserModel userModel = new UserModel();
        int userId = userModel.getUserIdByAuth(email.toLowerCase(), password);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(remoteAddr)
                .setSubject(Integer.toString(userId))
                .signWith(SignatureAlgorithm.HS512, secretKey);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        jwtBuilder.setIssuedAt(cal.getTime());

        if(!rememberMe) {
            cal.add(Calendar.HOUR_OF_DAY, 1);
            date = cal.getTime();

            jwtBuilder.setExpiration(date);
        }

        String jwtAuthorization = jwtBuilder.compact();

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
            String JWTUserId = Jwts.parser()
                    .requireIssuer(remoteAddr)
                    .setSigningKey(secretKey)
                    .parseClaimsJws(authorization)
                    .getBody()
                    .getSubject();

            authUserId = Integer.parseInt(JWTUserId);

            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException e) {
            return false;
        }
    }

    public String generateBackofficeUserAuth(String email, String password, String remoteAddr) throws IOException, ClassNotFoundException, UnAuthorizedException, SQLException {
        secretKey = Config.getConfig("jwt.secret");
        return generateAuth(email, password, remoteAddr, false);
    }

    public String generateUserAuth(String email, String password, String remoteAddr, boolean rememberMe) throws UnAuthorizedException, IOException, SQLException, ClassNotFoundException {
        secretKey = Config.getConfig("backoffice.jwt.secret");
        return generateAuth(email, password, remoteAddr, rememberMe);
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
