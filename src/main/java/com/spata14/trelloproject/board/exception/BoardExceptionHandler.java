package com.spata14.trelloproject.board.exception;

import com.spata14.trelloproject.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BoardExceptionHandler {
    @ExceptionHandler(UserException.class)

    public ResponseEntity<Map<String, Object>> handleCustomException(UserException ex) {
        return getMapResponseEntity(ex.getUserErrorCode().toString(), ex.getMessage(), ex.getUserErrorCode().getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(fieldErrors);
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