package com.authobusy.service.access;

import com.authobusy.service.access.request.PasswordChangeRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto changePassword(PasswordChangeRequest request);
}
