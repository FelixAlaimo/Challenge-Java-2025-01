package com.challenge.ventas.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice(basePackages = {"com.challenge.ventas.controller.impl"})
public class GlobalExceptionHandler {
	
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";
	private static final String ERROR_KEY = "error";
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MissingRequiredFieldException.class)
    public ResponseEntity<Map<String, String>> handleMissingRequiredField(MissingRequiredFieldException ex) {
    	return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, String>> handleBusinessRule(BusinessRuleException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    
    @ExceptionHandler({NumberFormatException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Map<String, String>> handleNumberFormatException(NumberFormatException ex) {
    	logger.error("Invalid number format error: {}", ex.getMessage());
    	
    	Map<String, String> response = new HashMap<>();
    	response.put(ERROR_KEY, "Invalid number format");
    	response.put(MESSAGE_KEY, "Se esperaba un valor numerico pero no fue provisto correctamente.");
    	
    	return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler({Exception.class, IllegalStateException.class})
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
    	logger.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        return buildErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(ERROR_KEY, message);
        errorResponse.put(STATUS_KEY, status.toString());
        return ResponseEntity.status(status).body(errorResponse);
    }

}
