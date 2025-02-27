package com.challenge.ventas.exception;

public class MissingRequiredFieldException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public MissingRequiredFieldException(String message) {
        super(message);
    }

}
