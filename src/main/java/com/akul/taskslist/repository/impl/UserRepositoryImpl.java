package com.akul.taskslist.repository.impl;

import com.akul.taskslist.domain.user.Role;
import com.akul.taskslist.domain.user.User;
import com.akul.taskslist.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }

    @Override
    public void insertUserRole(Long userId, Role role) {

    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }
}
