package com.ecosystem.todojava.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoNotFoundTest {

    @Test
    void todoNotFoundException() {
        String id = "123";
        TodoNotFound ex = new TodoNotFound(id);
        assertEquals("Todo Not Found with id: 123", ex.getMessage());
    }

}