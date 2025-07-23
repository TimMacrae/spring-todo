package com.ecosystem.todojava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("todo")
@AllArgsConstructor
public class Todo {
    private String id;
    private String description;
    private TodoStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
