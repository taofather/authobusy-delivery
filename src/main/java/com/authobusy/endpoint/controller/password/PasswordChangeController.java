package com.authobusy.endpoint.controller.password;

import com.authobusy.endpoint.controller.password.request.PasswordChangeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

@RestController
public class PasswordChangeController {

    @RequestMapping("/passchange")
    public String get(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            PasswordChangeRequest command = new ObjectMapper()
                .readValue(request.getInputStream(), PasswordChangeRequest.class);

            command.assertIsValid();

            return "{ success: \"ok\" }";

        } catch (IOException ioe) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "{ error: " + ioe.getMessage() + "}";
        } catch (InvalidParameterException inve) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "{ error: " + inve.getMessage() + "}";
        }

    }
}
