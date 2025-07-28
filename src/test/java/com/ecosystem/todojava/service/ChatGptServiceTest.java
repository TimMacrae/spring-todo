package com.ecosystem.todojava.service;

import com.ecosystem.todojava.dto.ChatGptCheckTodoSpellingRequest;
import com.ecosystem.todojava.dto.ChatGptResponse;
import com.ecosystem.todojava.exception.ChatGptSpellingRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ChatGptServiceTest {

    @Mock
    private RestClient.Builder restClientBuilder;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RestClient.RequestBodySpec requestBodySpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private ChatGptService chatGptService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        when(restClientBuilder.baseUrl(anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);

        chatGptService = new ChatGptService(restClientBuilder, "fake-api-key", "http://fake-url");
    }

    @Test
    void testCheckTodoSpelling_success() {
        String todo = "Chek spelling";
        ChatGptResponse expectedResponse = new ChatGptResponse(); // Customize as needed

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("")).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(ChatGptCheckTodoSpellingRequest.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(ChatGptResponse.class)).thenReturn(expectedResponse);


        ChatGptResponse actualResponse = chatGptService.checkTodoSpelling(todo);

        assertEquals(expectedResponse, actualResponse);
        verify(restClient).post();
    }

    @Test
    void testCheckTodoSpelling_failure_throwsException() {
        String todo = "Chek spelling";

        when(restClient.post()).thenThrow(new RuntimeException("API error"));

        ChatGptSpellingRequestException ex = assertThrows(
                ChatGptSpellingRequestException.class,
                () -> chatGptService.checkTodoSpelling(todo)
        );

        assertTrue(ex.getMessage().contains("API error"));
    }


}