package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.*;

@Controller
public class MyController {

    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public MyController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    /**
     * [
     *   "/getMappings",
     *   "/getMappings2",
     *   "/test/test1",
     *   "/test/test2",
     *   "/error",
     *   "/error"
     * ]
     */
    @RequestMapping("/getMappings")
    @ResponseBody
    public List<String> getAllRequestMappings() {
        List<String> mappings = new ArrayList<>();
        for (RequestMappingInfo info : handlerMapping.getHandlerMethods().keySet()) {
            mappings.addAll(info.getPatternsCondition().getPatterns());
        }
        return mappings;
    }

    /**
     {
         "org.example.controller.TestController": [
             "{GET /test/test1}",
             "{GET /test/test2}"
         ],
         "org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController": [
             "{ /error}",
             "{ /error, produces [text/html]}"
         ],
         "org.example.controller.MyController": [
             "{ /getMappings}",
             "{ /getMappings2}"
         ]
     }
     */
    @RequestMapping("/getMappings2")
    @ResponseBody
    public Map<String, List<String>> getAllControllerRequestMapping() {
        Map<String, List<String>> mappings = new HashMap<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet()) {
            RequestMappingInfo info = entry.getKey();

            String controller = entry.getValue().toString().split("#")[0];

            if (mappings.containsKey(controller)) {
                mappings.get(controller).add(info.toString());
            } else {
                List<String> methods = new ArrayList<>();
                methods.add(info.toString());
                mappings.put(controller, methods);
            }
        }
        return mappings;
    }
}