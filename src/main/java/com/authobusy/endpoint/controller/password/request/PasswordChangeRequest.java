package com.authobusy.endpoint.controller.password.request;

import com.authobusy.domain.valueobject.EmailValue;
import com.authobusy.endpoint.request.ValidableRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.security.InvalidParameterException;

public class PasswordChangeRequest implements Serializable, ValidableRequest {

    private String oldPassword;

    private String username;

    private String newPassword;

    @JsonCreator
    public PasswordChangeRequest(
            @JsonProperty("oldpassword") String oldPassword,
            @JsonProperty("newpassword") String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getUsername() {
        return this.username;
    }

    public void assertIsValid() throws InvalidParameterException {
        if (this.username.isEmpty() ||
            this.oldPassword.isEmpty() ||
            this.newPassword.isEmpty()
        ) {
            throw new InvalidParameterException();
        }
    }

}