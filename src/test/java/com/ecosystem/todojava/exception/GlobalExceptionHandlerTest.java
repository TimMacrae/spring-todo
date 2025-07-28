package com.ecosystem.todojava.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
    @Test
    void handleAllExceptions_returnsInternalErrorMessage() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new RuntimeException("Something went wrong");

        ExceptionMessage result = handler.handleAllExceptions(ex);

        assertNotNull(result);
        assertEquals("Internal error: RuntimeException", result.message());
    }

    @Test
    void handleAllExceptions_handlesDifferentExceptionTypes() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new IllegalArgumentException("Invalid argument");

        ExceptionMessage result = handler.handleAllExceptions(ex);

        assertNotNull(result);
        assertEquals("Internal error: IllegalArgumentException", result.message());
    }
}