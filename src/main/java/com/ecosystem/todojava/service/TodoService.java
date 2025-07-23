package com.ecosystem.todojava.service;

import com.ecosystem.todojava.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoService {
    private TodoRepository todoRepository;
}
