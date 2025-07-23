package com.ecosystem.todojava.exception;

public class TodoCouldNotBeCreated extends RuntimeException {
    public TodoCouldNotBeCreated(String description) {
        super("Todo " + description + " could not be created");
    }
}
