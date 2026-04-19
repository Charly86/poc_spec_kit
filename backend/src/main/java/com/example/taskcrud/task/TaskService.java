package com.example.taskcrud.task;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task(normalizeTitle(request.title()), normalizeDescription(request.description()));
        return TaskResponse.from(repository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> listTasks() {
        return repository.findAll().stream()
                .map(TaskResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTask(Long id) {
        return TaskResponse.from(findTask(id));
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = findTask(id);
        task.setTitle(normalizeTitle(request.title()));
        task.setDescription(normalizeDescription(request.description()));
        return TaskResponse.from(task);
    }

    @Transactional
    public TaskResponse completeTask(Long id) {
        Task task = findTask(id);
        task.setStatus(TaskStatus.COMPLETED);
        return TaskResponse.from(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = findTask(id);
        repository.delete(task);
    }

    private Task findTask(Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    private String normalizeTitle(String title) {
        return title.trim();
    }

    private String normalizeDescription(String description) {
        if (description == null) {
            return null;
        }
        String trimmed = description.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
