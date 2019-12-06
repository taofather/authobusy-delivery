package com.authobusy.service.access;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class UserDto extends User {

    public UserDto(com.authobusy.domain.user.User domainUser) {
        super(
            domainUser.getEmail().getValue(),
            domainUser.getPassword().getValue(),
            new ArrayList<>()
        );
    }
}