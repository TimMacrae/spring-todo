package com.ecosystem.todojava.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatGptSpellcheckIsNullTest {
    @Test
    void chatGptSpellcheckIsNull_constructor_setsCorrectMessage() {
        ChatGptSpellcheckIsNull ex = new ChatGptSpellcheckIsNull();
        assertEquals("Internal error: spell check returned null", ex.getMessage());
    }

    @Test
    void chatGptSpellcheckIsNull_isInstanceOfRuntimeException() {
        ChatGptSpellcheckIsNull ex = new ChatGptSpellcheckIsNull();
        assertInstanceOf(RuntimeException.class, ex);
    }
}