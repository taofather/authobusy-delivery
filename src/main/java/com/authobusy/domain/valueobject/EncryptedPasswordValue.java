package com.authobusy.domain.valueobject;

public class EncryptedPasswordValue {

    private final String value;

    public EncryptedPasswordValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
