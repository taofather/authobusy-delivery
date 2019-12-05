package com.authobusy.endpoint.security;

import com.authobusy.endpoint.controller.access.request.LoginRequest;
import com.authobusy.service.user.UserService;
import com.authobusy.service.user.UserDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService usersService;



    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService usersService) {
        this.usersService = usersService;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {

            LoginRequest creds = new ObjectMapper()
                    .readValue(req.getInputStream(), LoginRequest.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        // Get User Details from Database
        String userName = ((User) auth.getPrincipal()).getUsername();
        UserDetails userDto = usersService.loadUserByUsername(userName);

        // Generate GWT
        String token = Jwts.builder()
                .setSubject(userDto.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(WebSecurity.TOKEN_TTL)))
                .signWith(SignatureAlgorithm.HS512, WebSecurity.TOKEN_SECRET )
                .compact();

        res.addHeader("Token", token);
        res.addHeader("UserID", userDto.getUsername());
    }
}