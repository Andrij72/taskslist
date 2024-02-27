package com.akul.taskslist.repository.impl;

import com.akul.taskslist.domain.exception.ResourceMappingException;
import com.akul.taskslist.domain.exception.ResourceNotFoundException;
import com.akul.taskslist.domain.user.Role;
import com.akul.taskslist.domain.user.User;
import com.akul.taskslist.repository.DataSourceConfig;
import com.akul.taskslist.repository.UserRepository;
import com.akul.taskslist.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    private final static Logger lg = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final String FIND_BY_ID = """
            SELECT u.id              as user_id,
                   u.name          as user_name,
                   u.username        as user_username,
                   u.password        as user_password,
                   ur.role           as user_role_role,
                   t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            FROM taskslist.users u
                     JOIN taskslist.users_roles ur on u.id = ur.user_id
                     JOIN taskslist.users_tasks ut on u.id = ut.user_id
                     JOIN taskslist.tasks t on t.id = ut.task_id
            WHERE u.id = ?""";

    private final String FIND_BY_USER = """                                      
            SELECT u.id              as user_id,
                   u.name            as user_name,
                   u.username        as user_username,
                   u.password        as user_password,
                   ur.role           as user_role_role,
                   t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            FROM taskslist.users u
            JOIN taskslist.users_roles ur on u.id = ur.user_id
            JOIN taskslist.users_tasks ut on u.id = ut.user_id
            JOIN taskslist.tasks t on t.id = ut.task_id
            WHERE u.username = ?
             """;

    private final String UPDATE = """
            UPDATE taskslist.users
            SET name = ?,
                username = ?,
                password = ?
            WHERE id = ?""";

    private final String CREATE = """
            INSERT INTO taskslist.users (name, username, password)
            VALUES (?, ?, ?)""";

    private final String DELETE = """
            DELETE FROM taskslist.users
            WHERE id = ?""";

    private final String IS_TASK_OWNER = """
            SELECT exists(
            SELECT 1
            FROM taskslist.users_tasks
            WHERE user_id = ?
            AND   task_id = ?
                   )""";


    private final String INSERT_USER_ROLE = """
            INSERT INTO taskslist.users_roles (user_id, role)
            VALUES (?, ?)""";

    @Override
    public Optional<User> findUserById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceNotFoundException("Exception wile find user by id", e);
        }

    }

    @Override
    public Optional<User> findUserByUsername(String usernameValue) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USER,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, usernameValue);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceNotFoundException("Exception wile find user by username", e);
        }
    }


    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception wile update user.");
        }
    }

    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception wile create user.");
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setString(2, role.name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception wile inserting user role.");
        }

    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_TASK_OWNER,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, userId);
            statement.setLong(2, taskId);

            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking ist task owner.");
        }

    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception wile delete user.");
        }
    }

}