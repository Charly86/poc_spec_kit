package com.example.taskcrud.task;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:taskcrudtest;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class TaskControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clearData() {
        repository.deleteAll();
    }

    @Test
    void createsTaskWithPendingStatusAndLocation() throws Exception {
        mvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":" Comprar leche ","description":" Pasar por el supermercado "}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/tasks/")))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Comprar leche"))
                .andExpect(jsonPath("$.description").value("Pasar por el supermercado"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void rejectsBlankTitleOnCreate() throws Exception {
        mvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"   "}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.errors[0]", containsString("title")));
    }

    @Test
    void listsTasks() throws Exception {
        Long id = createTask("Comprar leche", null);

        mvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].title").value("Comprar leche"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }

    @Test
    void getsTaskDetail() throws Exception {
        Long id = createTask("Comprar leche", "Pasar por el supermercado");

        mvc.perform(get("/api/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Comprar leche"))
                .andExpect(jsonPath("$.description").value("Pasar por el supermercado"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void returnsNotFoundForMissingDetail() throws Exception {
        mvc.perform(get("/api/tasks/{id}", 999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task 999 not found"));
    }

    @Test
    void updatesTaskAndPreservesCreatedAt() throws Exception {
        Long id = createTask("Comprar leche", "Original");
        String createdAt = getCreatedAt(id);

        mvc.perform(put("/api/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Comprar pan","description":"Actualizar la compra"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Comprar pan"))
                .andExpect(jsonPath("$.description").value("Actualizar la compra"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.createdAt").value(createdAt));
    }

    @Test
    void rejectsBlankTitleOnUpdate() throws Exception {
        Long id = createTask("Comprar leche", "Original");

        mvc.perform(put("/api/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":" "}
                                """))
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Comprar leche"));
    }

    @Test
    void returnsNotFoundForMissingUpdate() throws Exception {
        mvc.perform(put("/api/tasks/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Comprar pan"}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void completesTaskIdempotently() throws Exception {
        Long id = createTask("Comprar leche", null);

        mvc.perform(patch("/api/tasks/{id}/complete", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        mvc.perform(patch("/api/tasks/{id}/complete", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void returnsNotFoundForMissingComplete() throws Exception {
        mvc.perform(patch("/api/tasks/{id}/complete", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletesTask() throws Exception {
        Long id = createTask("Comprar leche", null);

        mvc.perform(delete("/api/tasks/{id}", id))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        mvc.perform(get("/api/tasks/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnsNotFoundForMissingDelete() throws Exception {
        mvc.perform(delete("/api/tasks/{id}", 999))
                .andExpect(status().isNotFound());
    }

    private Long createTask(String title, String description) throws Exception {
        String body = objectMapper.writeValueAsString(new TaskRequest(title, description));
        MvcResult result = mvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.get("id").asLong();
    }

    private String getCreatedAt(Long id) throws Exception {
        MvcResult result = mvc.perform(get("/api/tasks/{id}", id))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.get("createdAt").asText();
    }
}
