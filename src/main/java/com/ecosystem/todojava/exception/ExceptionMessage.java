package com.ecosystem.todojava.exception;

import java.time.LocalDateTime;

public record ExceptionMessage(String message , LocalDateTime timestamp) {
    public ExceptionMessage(String message ){
        this(message,LocalDateTime.now());
    }
}
