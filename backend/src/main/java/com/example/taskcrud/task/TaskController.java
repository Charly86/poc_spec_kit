package com.example.taskcrud.task;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse task = service.createTask(request);
        return ResponseEntity.created(URI.create("/api/tasks/" + task.id())).body(task);
    }

    @GetMapping
    List<TaskResponse> listTasks() {
        return service.listTasks();
    }

    @GetMapping("/{id}")
    TaskResponse getTask(@PathVariable Long id) {
        return service.getTask(id);
    }

    @PutMapping("/{id}")
    TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return service.updateTask(id, request);
    }

    @PatchMapping("/{id}/complete")
    TaskResponse completeTask(@PathVariable Long id) {
        return service.completeTask(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
