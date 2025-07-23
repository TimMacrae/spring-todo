package com.ecosystem.todojava.exception;

public class TodoNotFound extends RuntimeException {
    public TodoNotFound(String id) {
        super("Todo Not Found with id: " + id);
    }
}
