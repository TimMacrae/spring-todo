package com.ecosystem.todojava.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatGptResponseTest {
    @Test
    void chatGptResponse_getFirstOutputText_returnsText_whenPresent() {
        ChatGptResponse.Output.Content content = new ChatGptResponse.Output.Content();
        content.setType("text");
        content.setText("create todo");

        ChatGptResponse.Output output = new ChatGptResponse.Output();
        output.setContent(List.of(content));

        ChatGptResponse response = new ChatGptResponse();
        response.setOutput(List.of(output));

        assertEquals("create todo", response.getFirstOutputText());
    }

    @Test
    void chatGptResponse_getFirstOutputText_returnsNull_whenOutputIsNull() {
        ChatGptResponse response = new ChatGptResponse();
        response.setOutput(null);
        assertNull(response.getFirstOutputText());
    }

    @Test
    void chatGptResponse_getFirstOutputText_returnsNull_whenOutputIsEmpty() {
        ChatGptResponse response = new ChatGptResponse();
        response.setOutput(List.of());
        assertNull(response.getFirstOutputText());
    }

    @Test
    void chatGptResponse_getFirstOutputText_returnsNull_whenContentIsNull() {
        ChatGptResponse.Output output = new ChatGptResponse.Output();
        output.setContent(null);

        ChatGptResponse response = new ChatGptResponse();
        response.setOutput(List.of(output));

        assertNull(response.getFirstOutputText());
    }

    @Test
    void chatGptResponse_getFirstOutputText_returnsNull_whenContentIsEmpty() {
        ChatGptResponse.Output output = new ChatGptResponse.Output();
        output.setContent(List.of());

        ChatGptResponse response = new ChatGptResponse();
        response.setOutput(List.of(output));

        assertNull(response.getFirstOutputText());
    }

    @Test
    void chatGptResponse_toString_includesOutput() {
        ChatGptResponse response = new ChatGptResponse();
        assertTrue(response.toString().contains("output="));
    }
}