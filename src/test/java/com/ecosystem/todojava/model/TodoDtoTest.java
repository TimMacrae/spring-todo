package com.ecosystem.todojava.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoDtoTest {
    @Test
    void todoDto_shouldBeCreatedCorrectly() {
        String description = "Test description";
        TodoStatus status = TodoStatus.OPEN;

        TodoDto dto = new TodoDto(description, status);

        assertEquals(description, dto.description());
        assertEquals(status, dto.status());
    }

}