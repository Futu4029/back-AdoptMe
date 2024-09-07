package com.unq.adopt_me.errorhandlers;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    HttpStatus httpStatus;

    public BusinessException(String message) {
        super(message, null, false, true); // disable suppression, enable stack trace
    }

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message, null, false, true);
        this.httpStatus = httpStatus;
    }
}