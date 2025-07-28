package com.ecosystem.todojava.service;

import com.ecosystem.todojava.dto.ChatGptCheckTodoSpellingRequest;
import com.ecosystem.todojava.dto.ChatGptResponse;
import com.ecosystem.todojava.exception.ChatGptSpellingRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
public class ChatGptService {

    private final RestClient restClient;

    public ChatGptService(
            RestClient.Builder restClientBuilder,
            @Value("${chatgpt.api.key}") String apiKey,
            @Value("${chatgpt.api.baseurl}") String baseUrl
    ) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public ChatGptResponse checkTodoSpelling(String todo) {
        try {
            ChatGptCheckTodoSpellingRequest request = new ChatGptCheckTodoSpellingRequest("gpt-4.1", todo);
            return restClient.post().uri("").body(request).retrieve().body(ChatGptResponse.class);
        } catch (Exception e) {
            throw new ChatGptSpellingRequestException(e.getMessage());
        }
    }
}
