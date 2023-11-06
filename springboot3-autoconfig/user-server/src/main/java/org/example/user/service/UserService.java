package org.example.user.service;

import org.example.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getUser(){
        User user = new User();
        user.setPassword("12312");
        user.setUsername("user");
        return user;
    }
}
