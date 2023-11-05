package org.example.controller;

import jakarta.annotation.Resource;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Resource
    UserService userService;

    @GetMapping("test")
    @ResponseBody
    User test(){
        return userService.getUser();
    }

}
