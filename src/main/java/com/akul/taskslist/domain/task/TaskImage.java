package com.akul.taskslist.domain.task;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TaskImage {

    private MultipartFile file;

}
