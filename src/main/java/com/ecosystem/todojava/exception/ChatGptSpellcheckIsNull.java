package com.ecosystem.todojava.exception;

public class ChatGptSpellcheckIsNull extends RuntimeException {
    public ChatGptSpellcheckIsNull() {
        super("Internal error: spell check returned null");
    }
}
