package com.akul.taskslist.domain.task;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime expirationDate;
    private Status status;
}
