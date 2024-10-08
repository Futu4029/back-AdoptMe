package com.unq.adopt_me.common;

import com.unq.adopt_me.security.SecurityConstants;
import io.jsonwebtoken.Jwts;

public class AbstractController {
//Ver de recuperar el id desde el context.
    public Long getIdFromToken(String authHeader){
        String token = authHeader.replace("Bearer ", "");
        return Long.valueOf((Integer) Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody().get("userId"));
    }
}
