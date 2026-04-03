package com.oorjacafe.oorjapay.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    //secret key (later will move to application.prop)
    private final Key SECRET_KEY= Keys.secretKeyFor(SignatureAlgorithm.HS384);

    //Token Validity(1 day)
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    //Generate Token
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)//payload
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();

    }

    //Extract email from token
    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }


}
