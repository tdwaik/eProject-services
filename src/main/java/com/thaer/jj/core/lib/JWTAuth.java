package com.thaer.jj.core.lib;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016.
 */

public class JWTAuth {

    private String secretKey;

    public JWTAuth(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public JWTAuth setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public String generateNewAuth(String isser, String subject) {

        Date expirationDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(expirationDate);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        expirationDate = cal.getTime();

        String jwtAuthorization = Jwts.builder()
                .setIssuer(isser)
                .setSubject(subject)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        String[] jwtAuthorizationParts = jwtAuthorization.split("\\.");

        jwtAuthorization = jwtAuthorizationParts[1] + "." + jwtAuthorizationParts[2];

        return jwtAuthorization;

    }

}
