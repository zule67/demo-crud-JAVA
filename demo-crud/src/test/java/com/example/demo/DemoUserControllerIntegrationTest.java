package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class DemoUserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void testGetAllUsers() throws Exception {
        repository.save(new User(null, "Alice", "alice@example.com"));

        mockMvc.perform(get("/users"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    void testAddUser() throws Exception {
        String userJson = "{\"name\":\"Bob\",\"email\":\"bob@example.com\"}";

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Bob"));
    }

    @Test
    void testUpdateUser() throws Exception {
        User saved = repository.save(new User(null, "Charlie", "charlie@example.com"));
        String updateJson = "{\"name\":\"CharlieUpdated\",\"email\":\"charlie.updated@example.com\"}";

        mockMvc.perform(put("/users/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("CharlieUpdated"));
    }

    @Test
    void testDeleteUser() throws Exception {
        User saved = repository.save(new User(null, "David", "david@example.com"));

        mockMvc.perform(delete("/users/" + saved.getId()))
               .andExpect(status().isNoContent());
    }
}
