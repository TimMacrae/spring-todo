package com.ecosystem.todojava.exception;

public class ChatGptSpellingRequestException extends RuntimeException {
    public ChatGptSpellingRequestException(String message) {
        super("Spelling check failed: " + message);
    }
}
