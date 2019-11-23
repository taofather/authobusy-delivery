package com.authobusy.domain.user;

import com.authobusy.domain.valueobject.EmailValue;
import com.authobusy.domain.valueobject.PasswordValue;

public class User {

    private EmailValue email;
    private PasswordValue password;

    public User(EmailValue email, PasswordValue password) {
        this.email = email;
        this.password = password;
    }

    public EmailValue getEmail() {
        return email;
    }

    public PasswordValue getPassword() {
        return password;
    }
}
