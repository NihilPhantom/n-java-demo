package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {
    @GetMapping("test1")
    @ResponseBody
    String test1(){
        return "ok";
    }

    @GetMapping("test2")
    @ResponseBody
    String test2(){
        return "ok";
    }
}