package com.ecosystem.todojava.dto;

public record ChatGptCheckTodoSpellingRequest(String model, String input) {
    public ChatGptCheckTodoSpellingRequest(String model, String input) {
        this.model = model;
        this.input = "Check spelling from for the TODO sentence. You only return the corrected sentence nothing else if the sentence is all ready spelt correct then just give me back the sentence without any changes. Don't add any unnecessary words or anything else just correct the sentence. Not return the TODO: this is just for your information, when the sentence begins. TODO: " + input;
    }
}
