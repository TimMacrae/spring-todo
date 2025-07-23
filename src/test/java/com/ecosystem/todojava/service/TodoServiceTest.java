package com.ecosystem.todojava.service;

import com.ecosystem.todojava.exception.TodoCouldNotBeCreated;
import com.ecosystem.todojava.model.Todo;
import com.ecosystem.todojava.model.TodoDto;
import com.ecosystem.todojava.model.TodoStatus;
import com.ecosystem.todojava.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    Todo todo1;
    Todo todo2;
    TodoDto todoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        todo1 = new Todo("1", "todo1", TodoStatus.OPEN, LocalDateTime.now(), null);
        todo2 = new Todo("2", "todo2", TodoStatus.OPEN, LocalDateTime.now(), LocalDateTime.now());
        todoDto = new TodoDto("todo1", TodoStatus.OPEN);
    }

    @Test
    void getAllTodos_shouldReturnTodos() {
        List<Todo> todos = List.of(todo1, todo2);
        Mockito.when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> response = todoService.getAllTodos();

        assertEquals(todos.size(), response.size());
        assertEquals(todos, response);
        Mockito.verify(todoRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getAllTodos_shouldReturnEmptyList() {
        List<Todo> todos = List.of();
        Mockito.when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> response = todoService.getAllTodos();

        assertEquals(0, response.size());
        assertEquals(todos, response);
        Mockito.verify(todoRepository, Mockito.times(1)).findAll();
    }

    @Test
    void createTodo_shouldCreateTodo() {
        Mockito.when(todoRepository.save(Mockito.any(Todo.class))).thenReturn(todo1);

        Todo todo = todoService.createTodo(todoDto);

        assertEquals(todoDto.description(), todo.getDescription());
        assertEquals(todoDto.status(), todo.getStatus());
        Mockito.verify(todoRepository, Mockito.times(1)).save(Mockito.any(Todo.class));
    }

    @Test
    void createTodo_shouldThrowTodoCouldNotBeCreated_whenRepositoryFails() {
        Mockito.when(todoRepository.save(Mockito.any(Todo.class)))
                .thenThrow(new RuntimeException("DB error"));

        TodoDto todoDto = new TodoDto("fail", TodoStatus.OPEN);

        assertThrows(TodoCouldNotBeCreated.class, () -> {
            todoService.createTodo(todoDto);
        });
    }

}