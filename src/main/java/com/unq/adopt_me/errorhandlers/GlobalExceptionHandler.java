package com.unq.adopt_me.errorhandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), ex.getHttpStatus().getReasonPhrase());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

}