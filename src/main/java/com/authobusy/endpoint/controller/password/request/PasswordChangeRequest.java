package com.authobusy.endpoint.controller.password.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PasswordChangeRequest implements Serializable {

    private String oldPassword;

    private String newPassword;

    @JsonCreator
    public PasswordChangeRequest(
            @JsonProperty("oldpassword") String oldPassword,
            @JsonProperty("newpassword") String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

}