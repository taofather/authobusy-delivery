package com.authobusy.endpoint.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @RequestMapping("/check")
    public String get(@RequestParam(value="name", defaultValue="World") String name) {
        return "{ content: \"Hello, World!\"}";
    }
}
