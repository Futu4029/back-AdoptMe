package com.unq.adopt_me.security;

import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.errorhandlers.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

import static com.unq.adopt_me.service.impl.UserServiceImpl.ERROR_MESSAGE;

@Component
public class JWTTokenProvider {

    @Autowired
    private UserDao userDao;

    public String generarToken(Authentication authentication){
        Long userId = userDao.findByEmail(authentication.getName()).orElseThrow(
                ()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND))
                .getId();
        HashMap<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", userId);
        Date actualTime = new Date();
        Date expirationDate = new Date(actualTime.getTime() + SecurityConstants.JWT_EXPIRATION_TOKEN);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(actualTime)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_FIRMA)
                .compact();
    }

    public Long obtainIdFromJWT(String token){
        return Long.valueOf((Integer) Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody().get("userId"));
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SecurityConstants.JWT_FIRMA).parseClaimsJws(token);
            return Boolean.TRUE;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT token expired or is incorrect");
        }
    }
    public Boolean isExpiredToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SecurityConstants.JWT_FIRMA).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Date expiration = claims.getExpiration();

            return expiration.before(new Date());
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT token is expired");
        }
    }
}
