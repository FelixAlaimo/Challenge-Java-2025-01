package com.challenge.ventas.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.challenge.ventas.controller.impl.TestController;

@WebMvcTest(TestController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

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
        mockMvc.perform(post("/test/number-format"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void testGenericException() throws Exception {
        mockMvc.perform(get("/test/generic"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.status").value("500 INTERNAL_SERVER_ERROR"));
    }
}


