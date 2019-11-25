package com.authobusy.endpoint.controller.password;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordChangeController {

    @RequestMapping("/passchange")
    public String get(@RequestParam(value="name", defaultValue="World") String name) {
        return "{ content: \"Hello, World!\"}";
    }
}
