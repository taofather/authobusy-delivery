package com.authobusy.endpoint.request;

import java.security.InvalidParameterException;

public interface ValidableRequest {
    void assertIsValid() throws InvalidParameterException;
}
