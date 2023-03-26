package com.chubov.SpringTelegramBot.utils;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchResourceNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchExpiredJwtTokenException(ExpiredJwtException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                "Token is expired!",
                System.currentTimeMillis()
        ), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchResourceNotFoundException(BadTokenException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(),
                System.currentTimeMillis()
        ), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchResourceNotFoundException(MissingHeaderException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis()
        ), HttpStatus.BAD_REQUEST);
    }

}
