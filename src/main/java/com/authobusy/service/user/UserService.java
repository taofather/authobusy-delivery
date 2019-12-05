package com.authobusy.service.user;

import com.authobusy.endpoint.controller.password.request.PasswordChangeRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto changePassword(PasswordChangeRequest request);
}
