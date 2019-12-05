package com.authobusy.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto changePassword(String email, String oldPassword, String newPassword);
}
