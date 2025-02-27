package com.challenge.ventas.controller.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.exception.BusinessRuleException;
import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/not-found")
    public void throwNotFound() throws ResourceNotFoundException {
        throw new ResourceNotFoundException("Resource not found");
    }

    @GetMapping("/bad-request")
    public void throwBadRequest() throws MissingRequiredFieldException {
        throw new MissingRequiredFieldException("Missing required field");
    }

    @GetMapping("/business-rule")
    public void throwBusinessRule() throws BusinessRuleException {
        throw new BusinessRuleException("Business rule violated");
    }

    @GetMapping("/number-format")
    public void throwNumberFormat() {
        throw new NumberFormatException("Invalid number format");
    }

    @GetMapping("/generic")
    public void throwGeneric() {
        throw new RuntimeException("Unexpected error");
    }
}
