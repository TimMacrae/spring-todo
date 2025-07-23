package com.ecosystem.todojava.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {
    @Test
    void todo_shouldBeCreatedCorrectly() {
        String id = "1";
        String description = "Test todo";
        TodoStatus status = TodoStatus.OPEN;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        Todo todo = new Todo(id,description,status,createdAt,updatedAt);

        assertEquals(id, todo.getId());
        assertEquals(description, todo.getDescription());
        assertEquals(status, todo.getStatus());
        assertEquals(createdAt, todo.getCreatedAt());
        assertEquals(updatedAt, todo.getUpdatedAt());
    }
}