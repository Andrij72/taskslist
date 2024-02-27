package com.akul.taskslist.service.impl;

import com.akul.taskslist.domain.exception.ResourceNotFoundException;
import com.akul.taskslist.domain.user.Role;
import com.akul.taskslist.domain.user.User;
import com.akul.taskslist.repository.UserRepository;
import com.akul.taskslist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Override
    @Transactional
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         userRepository.update(user);
         return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        if(userRepository.findUserByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("User already exist.");
        }
        if(!user.getPassword().equals(user.getPasswordConfirmation())){
            throw new IllegalStateException("Password und ConfirmPassword aren't equally ");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.create(user);
        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

}
