package com.authobusy.repository;

import com.authobusy.domain.user.User;
import com.authobusy.domain.valueobject.EmailValue;
import com.authobusy.domain.valueobject.EncryptedPasswordValue;
import com.authobusy.service.crypt.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class UsersRepository {

    private Map<String, User> database = new HashMap<>();

    public UsersRepository() {
        PasswordEncoder encoder = new PasswordEncoder();
        String pass = encoder.encode("123123");

        database.put("pepito@test.com", new User(
            new EmailValue("pepito@test.com"),
            new EncryptedPasswordValue(pass)
        ));
    }

    public User findByEmail(String email) {
        return database.get(email);
    }

    public void persist(User user) {
        database.put(user.getEmail().getValue(), user);
    }

}