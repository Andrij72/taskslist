package com.akul.taskslist.web.mappers;

import com.akul.taskslist.domain.task.TaskImage;
import com.akul.taskslist.web.dto.task.TaskImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDto> {
}
