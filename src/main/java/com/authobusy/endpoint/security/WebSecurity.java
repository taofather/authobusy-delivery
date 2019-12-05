package com.authobusy.endpoint.security;

import com.authobusy.service.crypt.PasswordEncoder;
import com.authobusy.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    public static final String TOKEN_SECRET = "h4of9eh48vmg02nfu30v27yen295hfj65";

    static final String TOKEN_TTL = "43200000";

    private final UserService userService;

    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurity(
            UserService usersService,
            PasswordEncoder bCryptPasswordEncoder) {
        this.userService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();

        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/check").permitAll();

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/login")
            .permitAll()
        .and()
            .addFilter(getAuthenticationFilter());

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/passchange")
                .permitAll()
                .anyRequest().authenticated();

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