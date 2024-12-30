package com.spata14.trelloproject.common.exception;

import com.spata14.trelloproject.card.exception.CardException;
import com.spata14.trelloproject.user.exception.UserException;
import com.spata14.trelloproject.workspace.exception.WorkspaceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    protected ResponseEntity<Map<String, Object>> handleUserException(UserException ex) {
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

    @ExceptionHandler(WorkspaceException.class)
    protected ResponseEntity<Map<String, Object>> handleWorkspaceException(WorkspaceException ex) {
        return getMapResponseEntity(ex.getWorkspaceErrorCode().toString(), ex.getMessage(), ex.getWorkspaceErrorCode().getHttpStatus());
    }

    @ExceptionHandler(CardException.class)
    protected ResponseEntity<Map<String, Object>> handleCardException(CardException ex) {
        return getMapResponseEntity(ex.getCardErrorCode().toString(), ex.getMessage(), ex.getCardErrorCode().getHttpStatus());
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
