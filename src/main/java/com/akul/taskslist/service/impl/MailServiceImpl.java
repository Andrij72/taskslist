package com.akul.taskslist.service.impl;

import com.akul.taskslist.domain.MailType;
import com.akul.taskslist.domain.user.User;
import com.akul.taskslist.service.MailService;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final Configuration configuration;
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(final User user,
                          final MailType mailType,
                          final Properties params) {
        switch (mailType) {
            case REMINDER -> sendReminderEmail(user, params);
            case REGISTRATION -> sendRegistrationEmail(user, params);
            default -> {
            }
        }
    }

    @SneakyThrows
    private void sendRegistrationEmail(final User user,
                                       final Properties params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                false, "UTF8");
        messageHelper.setSubject("Thank you for registration in system, "
                + user.getName());
        messageHelper.setTo(user.getUsername());
        String emailContent = getRegistrationEmailContent(user, params);
        messageHelper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private void sendReminderEmail(final User user, final Properties params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                false, "UTF8");
        messageHelper.setSubject("You have task to do in 1 hour ");
        messageHelper.setTo(user.getUsername());
        String emailContent = getReminderEmailContent(user, params);
        messageHelper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getReminderEmailContent(final User user,
                                           final Properties params) {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName());
        model.put("title", params.getProperty("task.title"));
        model.put("description", params.getProperty("task.description"));
        configuration.getTemplate("reminder.ftlh")
                .process(model, writer);
        return writer.getBuffer().toString();
    }

    @SneakyThrows
    private String getRegistrationEmailContent(final User user,
                                               final Properties params) {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName());
        configuration.getTemplate("register.ftlh")
                .process(model, writer);
        return writer.getBuffer().toString();
    }
}
