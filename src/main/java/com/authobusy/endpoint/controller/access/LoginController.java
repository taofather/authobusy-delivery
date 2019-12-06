package com.authobusy.endpoint.controller.access;

import com.authobusy.service.access.request.LoginRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/login")
    public String get(@RequestParam LoginRequest request) {
        return "{ content: \"success\"}";
    }
}
