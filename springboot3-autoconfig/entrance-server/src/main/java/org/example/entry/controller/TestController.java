package org.example.entry.controller;

import jakarta.annotation.Resource;

import org.example.sale.service.SaleService;
import org.example.user.entity.User;
import org.example.user.service.UserService;
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
