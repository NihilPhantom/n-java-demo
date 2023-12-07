package example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @GetMapping("test")
    @ResponseBody
    String test(){
        return "ok";
    }

    @GetMapping("err")
    @ResponseBody
    String testErr(){
        return "" + (123/0);
    }
}