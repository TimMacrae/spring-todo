package com.ecosystem.todojava.service;

import com.ecosystem.todojava.exception.TodoCouldNotBeCreated;
import com.ecosystem.todojava.exception.TodoNotFound;
import com.ecosystem.todojava.model.Todo;
import com.ecosystem.todojava.model.TodoDto;
import com.ecosystem.todojava.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TodoService {
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo createTodo(TodoDto todoDto) {
        try {
            Todo todo = new Todo(UUID.randomUUID().toString(), todoDto.description(), todoDto.status(), LocalDateTime.now(), null);
            return todoRepository.save(todo);
        } catch (Exception e) {
            throw new TodoCouldNotBeCreated(todoDto.description());
        }
    }

    public Todo getTodoById(String id) {
        return todoRepository.findById(id).orElseThrow(() -> new TodoNotFound(id));
    }

    public Todo updateTodo(String id, TodoDto todoDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFound(id));

        todo.setDescription(todoDto.description());
        todo.setStatus(todoDto.status());
        todo.setUpdatedAt(LocalDateTime.now());

        return todoRepository.save(todo);
    }

    public String deleteTodo(String id) {
            todoRepository.findById(id).orElseThrow(() -> new TodoNotFound(id));
            todoRepository.deleteById(id);
            return id;
    }
}
