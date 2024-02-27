package com.akul.taskslist.web.controller;

import com.akul.taskslist.domain.task.Task;
import com.akul.taskslist.service.TaskService;
import com.akul.taskslist.web.dto.task.TaskDto;
import com.akul.taskslist.web.mappers.TaskMapper;
import com.akul.taskslist.web.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {

    private final TaskMapper taskMapper;

    private final TaskService taskService;

    @GetMapping("/{id}")
    @Operation(summary = "Get taskDto")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @PutMapping
    @Operation(summary = "Update task")
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task updTask = taskService.update(task);
        return taskMapper.toDto(updTask);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task by user id")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }

}

