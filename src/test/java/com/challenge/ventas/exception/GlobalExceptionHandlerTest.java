package com.challenge.ventas.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(GlobalExceptionHandler.class)
@Import(GlobalExceptionHandlerTest.TestController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @RestController
    @RequestMapping("/test")
    static class TestController {

        @GetMapping("/not-found")
        public void throwNotFound() {
            throw new ResourceNotFoundException("Resource not found");
        }

        @GetMapping("/bad-request")
        public void throwBadRequest() {
            throw new MissingRequiredFieldException("Missing required field");
        }

        @GetMapping("/business-rule")
        public void throwBusinessRule() {
            throw new BusinessRuleException("Business rule violated");
        }

        @GetMapping("/number-format")
        public void throwNumberFormat() {
            throw new NumberFormatException("Invalid number format");
        }

        @GetMapping("/method-not-supported")
        public void throwMethodNotSupported(HttpServletRequest request) throws HttpRequestMethodNotSupportedException {
            throw new HttpRequestMethodNotSupportedException(request.getMethod());
        }

        @GetMapping("/generic")
        public void throwGeneric() {
            throw new RuntimeException("Unexpected error");
        }
    }

    @Test
    void testResourceNotFoundException() throws Exception {
        mockMvc.perform(get("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"));
    }

    @Test
    void testMissingRequiredFieldException() throws Exception {
        mockMvc.perform(get("/test/bad-request"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Missing required field"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"));
    }

    @Test
    void testBusinessRuleException() throws Exception {
        mockMvc.perform(get("/test/business-rule"))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.error").value("Business rule violated"))
                .andExpect(jsonPath("$.status").value("406 NOT_ACCEPTABLE"));
    }

    @Test
    void testNumberFormatException() throws Exception {
        mockMvc.perform(get("/test/number-format"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid number format"))
                .andExpect(jsonPath("$.message").value("Se esperaba un valor numerico pero no fue provisto correctamente."));
    }

    @Test
    void testHttpRequestMethodNotSupportedException() throws Exception {
        mockMvc.perform(post("/test/method-not-supported"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void testGenericException() throws Exception {
        mockMvc.perform(get("/test/generic"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.status").value("500 INTERNAL_SERVER_ERROR"));
    }
}


