package com.ecosystem.todojava.controller;

import com.ecosystem.todojava.exception.TodoCouldNotBeCreated;
import com.ecosystem.todojava.model.Todo;
import com.ecosystem.todojava.model.TodoDto;
import com.ecosystem.todojava.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@AllArgsConstructor
public class TodoController {
    private final TodoService todoService;


    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody TodoDto todoDto) {
        Todo todo = todoService.createTodo(todoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }

    @ExceptionHandler(TodoCouldNotBeCreated.class)
    public ResponseEntity<?> handleNoCharacterWithIdFound(TodoCouldNotBeCreated ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                java.util.Map.of("message", ex.getMessage())
        );
    }

}
