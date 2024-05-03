package com.akul.taskslist.service.impl;

import com.akul.taskslist.domain.MailType;
import com.akul.taskslist.domain.task.Task;
import com.akul.taskslist.domain.user.User;
import com.akul.taskslist.service.MailService;
import com.akul.taskslist.service.Reminder;
import com.akul.taskslist.service.TaskService;
import com.akul.taskslist.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Service
@AllArgsConstructor
public class ReminderServiceImpl implements Reminder {

    private final Logger log = LoggerFactory
            .getLogger(ReminderServiceImpl.class);

    private final UserService userService;
    private final TaskService taskService;

    private final MailService mailService;
    private final Duration duration = Duration.ofMinutes(60);

    @Scheduled(cron = "0 */10 * * * *")
    @Override
    public void remindForTask() {

        List<Task> tasks = taskService.getAllSoonTasks(duration);
        tasks.forEach(task -> {
            User user = userService.getTaskAuthor(task.getId());
            Properties properties = new Properties();
            properties.setProperty("task.title", task.getTitle());
            properties.setProperty("task.description", task.getDescription());
            mailService.sendEmail(user, MailType.REMINDER, properties);
            log.info("Mail <<REMIND>> is sent for task"
                    + task.getDescription());
        });

    }

}
