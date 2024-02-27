package com.akul.taskslist.web.dto.task;

import com.akul.taskslist.domain.task.Status;
import com.akul.taskslist.web.validation.OnCreate;
import com.akul.taskslist.web.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
public class TaskDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Title must be not null.", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Title length must be smaller than 255")
    private String title;

    @Length(max = 255, message = "Title length must be smaller than 255", groups = {OnUpdate.class, OnCreate.class})
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

    private Status status;

}
