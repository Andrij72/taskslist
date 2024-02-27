package com.akul.taskslist.service;

import com.akul.taskslist.domain.task.Task;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUserId(Long id);

    Task update(Task task);

    Task create(Task task, Long userId);

    void delete(Long id);

}
