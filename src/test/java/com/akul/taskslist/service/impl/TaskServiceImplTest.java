package com.akul.taskslist.service.impl;

import com.akul.taskslist.config.TestConfig;
import com.akul.taskslist.domain.exception.ResourceNotFoundException;
import com.akul.taskslist.domain.task.Status;
import com.akul.taskslist.domain.task.Task;
import com.akul.taskslist.domain.task.TaskImage;
import com.akul.taskslist.repository.TaskRepository;
import com.akul.taskslist.service.ImageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private ImageService imageService;

    @Autowired
    private TaskServiceImpl taskService;

    public TaskServiceImplTest(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @Test
    void getById() {
        Long id = 1L;
        Task task = new Task();
        task.setId(id);
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        Task testServiceTask = taskService.getById(id);
        Mockito.verify(taskRepository).findById(id);
        Assertions.assertEquals(task, testServiceTask);
    }

    @Test
    void getByIdWhenIdNotExist() {
        Long id = 1L;
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> taskService.getById(id));
        Mockito.verify(taskRepository).findById(id);
    }

    @Test
    void getAllByUserId() {
        Long userId = 1L;
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            tasks.add(new Task());
        }
        Mockito.when(taskRepository.findAllByUserId(userId)).thenReturn(tasks);
        List<Task> testTasks = taskService.getAllByUserId(userId);
        Mockito.verify(taskRepository).findAllByUserId(userId);
        Assertions.assertEquals(tasks, testTasks);
    }

    @Test
    void update() {
        Long userId = 1L;
        Task task = new Task();
        task.setId(userId);
        task.setTitle("title");
        task.setStatus(Status.DONE);
        task.setDescription("description");
        task.setExpirationDate(LocalDateTime.now());
        Task testTask = taskService.update(task);
        Mockito.verify(taskRepository).save(task);
        Assertions.assertEquals(testTask, task);
    }

    @Test
    void updateWithNullStatus() {
        Long userId = 1L;
        Task task = new Task();
        task.setId(userId);
        task.setTitle("title");
        task.setDescription("description");
        task.setExpirationDate(LocalDateTime.now());
        Task testTask = taskService.update(task);
        Mockito.verify(taskRepository).save(task);
        Assertions.assertEquals(Status.TODO, testTask.getStatus());
    }

    @Test
    void create() {
        Long taskId = 1L;
        Long userId = 1L;
        Task task = new Task();
        Mockito.doAnswer(invocationOnMock -> {
            Task saveTask = invocationOnMock.getArgument(0);
            saveTask.setId(taskId);
            return saveTask;
        }).when(taskRepository).save(task);
        Task testTask = taskService.create(task, userId);
        Mockito.verify(taskRepository).save(task);
        Assertions.assertNotNull(testTask.getId());
        Mockito.verify(taskRepository).assignTask(userId, taskId);
    }

    @Test
    void delete() {
        Long taskId = 1L;
        taskService.delete(taskId);
        Mockito.verify(taskRepository).deleteById(taskId);
    }

    @Test
    void uploadImage() {
        Long id = 1L;
        String imageName = "imageName";
        TaskImage taskImage = new TaskImage();
        Mockito.when(imageService.upload(taskImage)).thenReturn(imageName);
        taskService.uploadImage(id, taskImage);
        Mockito.verify(taskRepository).addImage(id, imageName);
    }

}
