package com.ecosystem.todojava.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatGptResponse {
    @Override
    public String toString() {
        return "ChatGptResponse{" +
                "output=" + output +
                '}';
    }

    private List<Output> output;

    @Setter
    @Getter
    public static class Output {
        private List<Content> content;

        @Setter
        @Getter
        public static class Content {
            private String type;
            private String text;

        }
    }

    public String getFirstOutputText() {
        if (output != null && !output.isEmpty()) {
            Output firstOutput = output.getFirst();
            if (firstOutput.getContent() != null && !firstOutput.getContent().isEmpty()) {
                return firstOutput.getContent().getFirst().getText();
            }
        }
        return null;
    }
}
