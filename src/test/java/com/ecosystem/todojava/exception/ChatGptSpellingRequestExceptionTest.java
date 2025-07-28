package com.ecosystem.todojava.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatGptSpellingRequestExceptionTest {
    @Test
    void chatGptSpellingRequestException_constructor_setsCorrectMessage() {
        ChatGptSpellingRequestException ex = new ChatGptSpellingRequestException("API error");
        assertEquals("Spelling check failed: API error", ex.getMessage());
    }

    @Test
    void chatGptSpellingRequestException_isInstanceOfRuntimeException() {
        ChatGptSpellingRequestException ex = new ChatGptSpellingRequestException("test");
        assertInstanceOf(RuntimeException.class, ex);
    }

}