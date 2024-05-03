package com.akul.taskslist.config;


import com.akul.taskslist.repository.TaskRepository;
import com.akul.taskslist.repository.UserRepository;
import com.akul.taskslist.service.AuthService;
import com.akul.taskslist.service.ImageService;
import com.akul.taskslist.service.TaskService;
import com.akul.taskslist.service.impl.*;
import com.akul.taskslist.service.props.JwtProperties;
import com.akul.taskslist.service.props.MinioProperties;
import com.akul.taskslist.web.security.JwtTokenProvider;
import com.akul.taskslist.web.security.JwtUserDetailsService;
import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@TestConfiguration
@AllArgsConstructor
public class TestConfig {

    @Bean
    @Primary
    public PasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret("d0VyRmdIdkRmSktJb1V5Z3NyZWRuc2RpZnl0dXI3amR");
        return jwtProperties;
    }

    @Bean
    public JavaMailSender mailSender() {
        return  Mockito.mock(JavaMailSender.class);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new JwtUserDetailsService(userService(userRepository()));
    }

    @Bean
    public MinioClient minioClient() {
        return Mockito.mock(MinioClient.class);
    }

    @Bean
    public MinioProperties minioProperties() {
        MinioProperties minioProperties = new MinioProperties();
        minioProperties.setBucket("images");
        return minioProperties;
    }

    @Bean
    @Primary
    public ImageService imageService() {
        return new ImageServiceImpl(minioClient(), minioProperties());
    }

    @Bean
    public JwtTokenProvider tokenProvider() {
        return new JwtTokenProvider(jwtProperties(),
                userDetailsService(), userService(userRepository()));
    }

    @Bean
    @Primary
    public Configuration configuration(){
        return Mockito.mock(Configuration.class);
    }

    @Bean
    @Primary
    public UserServiceImpl userService(
            final UserRepository userRepository) {
        return new UserServiceImpl(userRepository,
                testPasswordEncoder(), mailService());
    }

    @Bean
    @Primary
    public MailServiceImpl mailService(){
        return new MailServiceImpl(configuration(), mailSender());
    }

    @Bean
    @Primary
    public TaskService taskService(
            final TaskRepository taskRepository
    ) {
        return new TaskServiceImpl(taskRepository, imageService());
    }
    @Bean
    @Primary
    public AuthService authService() {
        return new AuthServiceImpl(authenticationManager(),
                userService(userRepository()),
                tokenProvider());
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public TaskRepository taskRepository() {
        return Mockito.mock(TaskRepository.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }

}
