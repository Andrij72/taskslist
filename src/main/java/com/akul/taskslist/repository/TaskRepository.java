package com.akul.taskslist.repository;

import com.akul.taskslist.domain.task.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;


public interface TaskRepository {

    Optional<Task> findTaskByUserId(Long userId);

    List<Task> findAllByUserId(Long userId);

    void assignToUser(@Param("taskId") Long taskId, @Param("userId") Long userId);

    void update(Task task);

    void create(Task task);

    void delete(Long id);

}
