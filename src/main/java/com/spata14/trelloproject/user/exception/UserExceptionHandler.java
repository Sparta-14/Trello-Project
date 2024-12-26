package com.spata14.trelloproject.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserException.class)

    public ResponseEntity<Map<String, Object>> handleCustomException(UserException ex) {
        return getMapResponseEntity(ex.getUserErrorCode().toString(), ex.getMessage(), ex.getUserErrorCode().getHttpStatus());
    }

    private static ResponseEntity<Map<String, Object>> getMapResponseEntity(String errorName, String errorMessage, HttpStatus httpStatus) {
        Map<String, Object> response = new HashMap<>();
        response.put(errorName, errorMessage);
        response.put("status", httpStatus);

        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }
}
