package com.unq.adopt_me.dto.security;

import lombok.Data;

@Data
public class AuthResponse {

    private String accesstoken;
    private String tokenType = "Bearer ";


    public AuthResponse(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
