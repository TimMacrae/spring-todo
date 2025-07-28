package com.ecosystem.todojava.service;

import com.ecosystem.todojava.dto.ChatGptResponse;
import com.ecosystem.todojava.exception.ChatGptSpellcheckIsNull;
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
    private ChangeHistoryService changeHistoryService;

    private ChatGptService chatGptService;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo createTodo(TodoDto todoDto) {
        String spellCheckedTodo = chatGptService.checkTodoSpelling(todoDto.description()).getFirstOutputText();
        if(spellCheckedTodo == null) {
            throw new ChatGptSpellcheckIsNull();
        }
        System.out.println("SPELLING" + spellCheckedTodo);

        try {
            Todo todo = new Todo(UUID.randomUUID().toString(), spellCheckedTodo, todoDto.status(), LocalDateTime.now(), null);
            Todo saved = todoRepository.save(todo);

            // Undo: delete the created todo
            changeHistoryService.recordChange(() -> todoRepository.deleteById(saved.getId()));

            return saved;
        } catch (Exception e) {
            throw new TodoCouldNotBeCreated(spellCheckedTodo);
        }
    }

    public Todo getTodoById(String id) {
        return todoRepository.findById(id).orElseThrow(() -> new TodoNotFound(id));
    }

    public Todo updateTodo(String id, TodoDto todoDto) {
        Todo oldTodo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFound(id));
        // Save a copy of the old state for undo
        Todo oldState = new Todo(oldTodo.getId(), oldTodo.getDescription(), oldTodo.getStatus(), oldTodo.getCreatedAt(), oldTodo.getUpdatedAt());

        oldTodo.setDescription(todoDto.description());
        oldTodo.setStatus(todoDto.status());
        oldTodo.setUpdatedAt(LocalDateTime.now());

        Todo updated = todoRepository.save(oldTodo);

        // Undo: restore the old state
        changeHistoryService.recordChange(() -> todoRepository.save(oldState));

        return updated;
    }

    public String deleteTodo(String id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFound(id));
        todoRepository.deleteById(id);

        // Undo: restore the deleted todo
        changeHistoryService.recordChange(() -> todoRepository.save(todo));
        return id;
    }
}
