package com.authobusy.endpoint.controller.access;

import com.authobusy.service.access.request.PasswordChangeRequest;
import com.authobusy.endpoint.security.WebSecurity;
import com.authobusy.service.access.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

@RestController
public class PasswordChangeController {

    private UserService userService;

    @Autowired
    public PasswordChangeController(
            UserService usersService
    ) {
        this.userService = usersService;
    }

    @RequestMapping("/passchange")
    public String get(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            final String requestTokenHeader = request.getHeader("Authorization");
            String jwtToken = requestTokenHeader.substring(7);

            Jws<Claims> jws = Jwts.parser()
                .setSigningKey(WebSecurity.TOKEN_SECRET)
                .parseClaimsJws(jwtToken);

            String email = jws.getBody().getSubject();

            PasswordChangeRequest command = new ObjectMapper()
                    .readValue(request.getInputStream(), PasswordChangeRequest.class);

            command.setUsername(email);

            this.userService.changePassword(command);

            return "{ message: \"Password changed for " + email + "\"}";

        } catch (IOException ioe) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "{ error: " + ioe.getMessage() + "}";
        } catch (InvalidParameterException inve) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "{ error: " + inve.getMessage() + "}";
        } catch (ExpiredJwtException expiredJwtE) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "{ error: \"Expired token\" }";
        }
    }
}
