package com.unq.adopt_me.errorhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), ex.getHttpStatus().getReasonPhrase());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDto> handleNullPointerException(NullPointerException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleException(BadCredentialsException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleException(MethodArgumentNotValidException ex) {
        List<ObjectError> list = ex.getBindingResult().getAllErrors();
        String allMessages = list.stream()
                .map(ObjectError::getDefaultMessage).filter(Objects::nonNull)
                .map(String::toLowerCase)
                .collect(Collectors.joining(", "));
        String formattedMessages = allMessages.substring(0, 1).toUpperCase() + allMessages.substring(1);
        ErrorResponseDto errorResponse = new ErrorResponseDto(formattedMessages, HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}