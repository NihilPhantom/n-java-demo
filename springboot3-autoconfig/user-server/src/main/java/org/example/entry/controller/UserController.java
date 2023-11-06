package org.example.controller;

import jakarta.annotation.Resource;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("user")
    @ResponseBody
    User test(){
        return userService.getUser();
    }
}