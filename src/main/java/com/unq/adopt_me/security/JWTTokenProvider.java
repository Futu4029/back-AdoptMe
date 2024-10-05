package com.unq.adopt_me.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {

    public String generarToken(Authentication authentication){
        String username = authentication.getName();
        Date actualTime = new Date();
        Date expirationDate = new Date(actualTime.getTime() + SecurityConstants.JWT_EXPIRATION_TOKEN);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(actualTime)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_FIRMA)
                .compact();
    }

    public String obtainUsernameFromJWT(String token){
        return Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SecurityConstants.JWT_FIRMA).parseClaimsJws(token);
            return Boolean.TRUE;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT token expired or is incorrect");
        }
    }
}
