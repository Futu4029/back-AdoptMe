package com.unq.adopt_me.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String accesstoken;
    private String tokenType = "Bearer ";


    public AuthResponse(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
