package com.unq.adopt_me.common;

import com.unq.adopt_me.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class AbstractController {

    public Long getIdFromToken(String authHeader){
        String token = authHeader.replace("Bearer ", "");
        return Long.valueOf((Integer) Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody().get("userId"));
    }
}
