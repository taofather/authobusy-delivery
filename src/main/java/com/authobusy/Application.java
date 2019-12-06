package com.authobusy;

import com.authobusy.domain.user.UsersRepository;
import com.authobusy.service.crypt.PasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({
    UsersRepository.class,
    PasswordEncoder.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
