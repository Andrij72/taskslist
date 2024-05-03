package com.akul.taskslist.service;

import com.akul.taskslist.domain.task.Task;
import com.akul.taskslist.domain.task.TaskImage;

import java.time.Duration;
import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUserId(Long id);

    Task update(Task task);

    Task create(Task task, Long userId);

    void delete(Long id);

    void uploadImage(Long id, TaskImage image);

    List<Task> getAllSoonTasks(Duration duration);
}
