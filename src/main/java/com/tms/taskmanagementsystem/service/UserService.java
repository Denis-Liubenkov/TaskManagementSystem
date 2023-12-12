package com.tms.taskmanagementsystem.service;

import com.tms.taskmanagementsystem.domain.User;
import org.springframework.stereotype.Service;
import com.tms.taskmanagementsystem.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        userRepository.save(user);
    }

    public void updateUser(User user) {
        user.setId(user.getId());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        userRepository.saveAndFlush(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }
}
