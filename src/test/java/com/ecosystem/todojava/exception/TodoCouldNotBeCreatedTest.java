package com.ecosystem.todojava.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoCouldNotBeCreatedTest {

    @Test
    void constructor_shouldSetCorrectMessage() {
        String description = "test";
        TodoCouldNotBeCreated ex = new TodoCouldNotBeCreated(description);
        assertEquals("Todo test could not be created", ex.getMessage());
    }

}