package com.unq.adopt_me.integralTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.security.AuthResponse;
import com.unq.adopt_me.dto.security.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthHelper {

    private String HTTP_LOCALHOST;
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    protected void setData(String HTTP_LOCALHOST, int port, TestRestTemplate restTemplate) {
        this.HTTP_LOCALHOST = HTTP_LOCALHOST;
        this.port = port;
        this.restTemplate = restTemplate;
    }

    protected String getToken(LoginDto dtoLogin) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(dtoLogin, headers);
        ResponseEntity<GeneralResponse> response = restTemplate.exchange(HTTP_LOCALHOST + port + "/auth/login", HttpMethod.POST, entity, GeneralResponse.class);
        ObjectMapper objectMapper = new ObjectMapper();
        AuthResponse authResponse = objectMapper.convertValue(response.getBody().getData(), AuthResponse.class);
        return authResponse.getAccesstoken();
    }
}
