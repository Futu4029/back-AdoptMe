package com.unq.adopt_me.errorhandlers;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto {

    private String message;
    private HttpStatus status;

    public ErrorResponseDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
