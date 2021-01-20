package com.appsdev.mobileapp.ws.shared;

import com.appsdev.mobileapp.ws.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Service
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateAddressId(int length) {
        return generateRandomString(length);
    }

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    public String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public boolean hasTokenExpired(String token) {
       boolean hasExpired = false;

       try {
           Claims claims = Jwts.parser()
                   .setSigningKey(SecurityConstants.getTokenSecret())
                   .parseClaimsJws(token).getBody();

           Date tokenExpirationDate = claims.getExpiration();
           Date tokenDate = new Date();

           hasExpired = tokenExpirationDate.before(tokenDate);
       } catch (ExpiredJwtException e) {
           hasExpired = true;
       }
       return hasExpired;
    }

    public String generateEmailVerificationToken(String userId) {
        return generateToken(userId, new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME));
    }

    public String generatePasswordResetToken(String userId) {
        return generateToken(userId, new Date(System.currentTimeMillis() + SecurityConstants.PASSWORD_RESET_EXPIRATION_TIME));

    }

    public String generateToken(String userId, Date expiration) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();

        return token;
    }
}
