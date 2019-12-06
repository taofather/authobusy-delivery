package com.authobusy.service.request;

import java.security.InvalidParameterException;

public interface ValidableRequest {

    /**
     * Conditions to pass to Application Layer
     *
     * @throws InvalidParameterException
     */
    void assertIsValid() throws InvalidParameterException;
}
