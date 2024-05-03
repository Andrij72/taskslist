package com.akul.taskslist.service;

import com.akul.taskslist.domain.MailType;
import com.akul.taskslist.domain.user.User;

import java.util.Properties;

public interface MailService {

    void sendEmail(User user, MailType mailType, Properties params);

}
