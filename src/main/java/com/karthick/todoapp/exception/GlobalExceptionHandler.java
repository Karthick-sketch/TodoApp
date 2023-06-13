package com.karthick.todoapp.exception;

import com.karthick.todoapp.common.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<APIResponse> handleNoSuchElementException(NoSuchElementException e) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        apiResponse.setError(e.getMessage());
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse> handleBadRequestException(BadRequestException e) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setError(e.getMessage());
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }
}
