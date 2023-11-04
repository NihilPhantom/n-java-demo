package com.nihil.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BLController {
    @Value("${server.port}")
    String port;

    @GetMapping("getPort")
    String getPort(){
        return port;
    }
}
