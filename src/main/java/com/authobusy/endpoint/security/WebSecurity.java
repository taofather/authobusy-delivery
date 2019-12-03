package com.authobusy.endpoint.security;

import com.authobusy.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    static final String TOKEN_TTL = "43200";

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurity(
            UserService usersService,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/check").permitAll();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(getAuthenticationFilter());

         http.headers().frameOptions().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(
            authenticationManager(),
            userService
        );
        return filter;
    }

}