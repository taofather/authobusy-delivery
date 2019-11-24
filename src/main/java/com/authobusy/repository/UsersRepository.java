package com.authobusy.repository;

import com.authobusy.domain.user.User;
import com.authobusy.domain.valueobject.EmailValue;
import com.authobusy.domain.valueobject.PasswordValue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class UsersRepository {

    private Map<String, User> database = new HashMap<>();

    public UsersRepository() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode("123123");

        database.put("pepito@test.com", new User(
            new EmailValue("pepito@test.com"),
            new PasswordValue(pass)
        ));
    }

    public User findByEmail(String email) {
        return database.get(email);
    }


}