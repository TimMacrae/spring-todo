package com.ecosystem.todojava.controller;

import com.ecosystem.todojava.model.Todo;
import com.ecosystem.todojava.model.TodoStatus;
import com.ecosystem.todojava.repository.TodoRepository;
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


@SpringBootTest()
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;


    @Test
    void getAllTodos_ShouldReturnAllTodos()  throws Exception {
        Todo todo = new Todo("1", "add tests", TodoStatus.OPEN, LocalDateTime.now(),null);
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
    void getAllTodos_ShouldReturnEmptyList()  throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                   []
                                   """
                ));
    }

    @Test
    void createTodo_ShouldReturnCreatedTodo()  throws Exception {
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
    void createTodo_ShouldThrowAnExceptionIfNotCreated()  throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo").contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {"description":"create todo",
                        "status": "WRONG_STATUS"
                        }
                        """
                ))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }


}