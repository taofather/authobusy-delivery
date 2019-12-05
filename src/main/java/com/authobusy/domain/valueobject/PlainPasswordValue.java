package com.authobusy.domain.valueobject;

import java.security.InvalidParameterException;

public class PlainPasswordValue {

    private final String value;

    public PlainPasswordValue(String value) throws InvalidParameterException {

        if (value.length() < 6 ||value.length() > 32) {
            throw new InvalidParameterException(
                "Password must be at least 6 characters, 32 max"
            );
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}