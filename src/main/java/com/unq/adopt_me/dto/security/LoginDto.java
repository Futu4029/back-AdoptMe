package com.unq.adopt_me.dto.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }


}
