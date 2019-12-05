package com.authobusy.domain.user;

import com.authobusy.domain.valueobject.EmailValue;
import com.authobusy.domain.valueobject.EncryptedPasswordValue;

public class User {

    private EmailValue email;
    private EncryptedPasswordValue password;

    public User(EmailValue email, EncryptedPasswordValue password) {
        this.email = email;
        this.password = password;
    }

    public EmailValue getEmail() {
        return email;
    }

    public EncryptedPasswordValue getPassword() {
        return password;
    }
}
