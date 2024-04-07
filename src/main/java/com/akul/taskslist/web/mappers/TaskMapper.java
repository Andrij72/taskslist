package com.akul.taskslist.web.mappers;

import com.akul.taskslist.domain.task.Task;
import com.akul.taskslist.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {
}
