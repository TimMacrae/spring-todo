package com.ecosystem.todojava.controller;

import com.ecosystem.todojava.model.Todo;
import com.ecosystem.todojava.model.TodoStatus;
import com.ecosystem.todojava.repository.TodoRepository;
import com.ecosystem.todojava.service.ChangeHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest()
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ChangeHistoryService changeHistoryService;


    @Test
    void getAllTodos_ShouldReturnAllTodos() throws Exception {
        Todo todo = new Todo("1", "add tests", TodoStatus.OPEN, LocalDateTime.now(), null);
        todoRepository.save(todo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                [{
                                "id": "1",
                                "description": "add tests",
                                "status": "OPEN",
                                "updatedAt": null
                                }]
                                """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdAt").isNotEmpty());
    }

    @Test
    void getAllTodos_ShouldReturnEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                []
                                """
                ));
    }

    @Test
    void createTodo_ShouldReturnCreatedTodo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo").contentType(MediaType.APPLICATION_JSON).content(
                        """
                                {"description":"create todo",
                                "status": "OPEN"
                                }
                                """
                ))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                "description": "create todo",
                                "status": "OPEN",
                                "updatedAt": null
                                }
                                """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    void createTodo_ShouldThrowAnGlobalExceptionIfNotCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo").contentType(MediaType.APPLICATION_JSON).content(
                        """
                                {"description":"create todo",
                                "status": "WRONG_STATUS"
                                }
                                """
                ))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(MockMvcResultMatchers.content().json("""
                        {"message": "Internal error: HttpMessageNotReadableException"}
                        """));
    }

    @Test
    void getTodoById_ShouldReturnTodoById() throws Exception {
        Todo todo = new Todo("1", "add tests", TodoStatus.OPEN, LocalDateTime.now(), null);
        todoRepository.save(todo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                "id": "1",
                                "description": "add tests",
                                "status": "OPEN",
                                "updatedAt": null
                                }
                                """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    void getTodoById_ShouldThrowAnExceptionIf_todoNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/5"))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content().json(
                        """
                                {"message": "Todo Not Found with id: 5"}
                                """
                ));
    }

    @Test
    void updateTodo_ShouldReturnTheUpdatedTodo() throws Exception {
        Todo todo = new Todo("1", "add tests", TodoStatus.OPEN, LocalDateTime.now(), null);
        todoRepository.save(todo);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/1").contentType(MediaType.APPLICATION_JSON).content(
                        """
                                {"description":"update todo",
                                "status": "IN_PROGRESS"
                                }
                                """
                ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                "description": "update todo",
                                "status": "IN_PROGRESS"
                                }
                                """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").isNotEmpty());
    }

    @Test
    void updateTodo_ShouldThrowAnExceptionIfTodoNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/5").contentType(MediaType.APPLICATION_JSON).content(
                        """
                                 {"description":"update todo",
                                  "status": "IN_PROGRESS"
                                  }
                                """
                ))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content().json(
                        """
                                {"message": "Todo Not Found with id: 5"}
                                """
                ));
    }

    @Test
    void deleteTodo_ShouldReturnTheDeletedId() throws Exception {
        Todo todo = new Todo("1", "add tests", TodoStatus.OPEN, LocalDateTime.now(), null);
        todoRepository.save(todo);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                "id": "1"
                                }
                                """
                ));
    }

    @Test
    void deleteTodo_ShouldThrowAnExceptionIfTodoNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/5"))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content().json(
                        """
                                {"message": "Todo Not Found with id: 5"}
                                """
                ));
    }

    @Test
    void undoLastChange_shouldUndoSuccessfully() throws Exception {
        String todoJson = "{\"description\":\"Test Undo\",\"status\":\"OPEN\"}";
        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoJson))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/todo/undo"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"Last change undone\"}"));
    }

    @Test
    void undoLastChange_shouldReturn404IfNoChange() throws Exception {
        mockMvc.perform(post("/api/todo/undo"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\": \"No changes to undo\"}"));
    }

    @Test
    void undoLastChange_shouldUndoCreate() throws Exception {
        // Create a todo
        String todoJson = "{\"description\":\"Test Undo\",\"status\":\"OPEN\"}";
        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoJson))
                .andExpect(status().isCreated());

        // Undo the creation
        mockMvc.perform(post("/api/todo/undo"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Last change undone")));

        // Verify todo is deleted
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


    @Test
    void undoLastChange_canUndoMultipleChanges() throws Exception {
        // Create two todos
        String todoJson1 = "{\"description\":\"Todo1\",\"status\":\"OPEN\"}";
        String todoJson2 = "{\"description\":\"Todo2\",\"status\":\"OPEN\"}";
        mockMvc.perform(post("/api/todo").contentType(MediaType.APPLICATION_JSON).content(todoJson1));
        mockMvc.perform(post("/api/todo").contentType(MediaType.APPLICATION_JSON).content(todoJson2));

        // Undo twice
        mockMvc.perform(post("/api/todo/undo")).andExpect(status().isOk());
        mockMvc.perform(post("/api/todo/undo")).andExpect(status().isOk());

        // Third undo should return 404
        mockMvc.perform(post("/api/todo/undo")).andExpect(status().isNotFound());
    }

}