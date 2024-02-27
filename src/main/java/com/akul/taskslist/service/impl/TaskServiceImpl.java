package com.akul.taskslist.service.impl;

import com.akul.taskslist.domain.exception.ResourceNotFoundException;
import com.akul.taskslist.domain.task.Status;
import com.akul.taskslist.domain.task.Task;
import com.akul.taskslist.repository.TaskRepository;
import com.akul.taskslist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public Task getById(Long id) {
        return taskRepository.findTaskByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public Task update(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.TODO);
        }
        taskRepository.update(task);
        return task;
    }

    @Override
    @Transactional
    public Task create(Task task, Long userId) {
        task.setStatus(Status.TODO);
        taskRepository.create(task);
        taskRepository.assignToUser(task.getId(), userId);
        return task;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.delete(id);
    }
}
