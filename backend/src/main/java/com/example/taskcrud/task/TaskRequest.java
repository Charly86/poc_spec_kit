package com.example.taskcrud.task;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
        @NotBlank(message = "must not be blank") String title,
        String description
) {
}
