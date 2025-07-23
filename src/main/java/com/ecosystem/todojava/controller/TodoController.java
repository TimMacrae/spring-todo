package com.ecosystem.todojava.controller;

import com.ecosystem.todojava.exception.TodoCouldNotBeCreated;
import com.ecosystem.todojava.exception.TodoNotFound;
import com.ecosystem.todojava.model.Todo;
import com.ecosystem.todojava.model.TodoDto;
import com.ecosystem.todojava.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}")
    public ResponseEntity<Todo>  getTodoById(@PathVariable String id) {
        Todo todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo>  updateTodo(@PathVariable String id, @RequestBody TodoDto todoDto) {
        Todo todo = todoService.updateTodo(id, todoDto);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTodo(@PathVariable String id) {
        String deletedId = todoService.deleteTodo(id);
        return  ResponseEntity.ok(Map.of("id", deletedId));
    }


    @ExceptionHandler(TodoNotFound.class)
    public ResponseEntity<?> handleTodoNotFound(TodoNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                java.util.Map.of("message", ex.getMessage())
        );
    }

    @ExceptionHandler(TodoCouldNotBeCreated.class)
    public ResponseEntity<?> handleNoCharacterWithIdFound(TodoCouldNotBeCreated ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                java.util.Map.of("message", ex.getMessage())
        );
    }

}
