package com.authobusy.endpoint.controller.access;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final String template = "Hello, %s!";

    @RequestMapping("/login")
    public String get(@RequestParam(value="name", defaultValue="World") String name) {
        return "{ content: \"Hello, World!\"}";
    }
}
