package com.authobusy.service.user;

import com.authobusy.domain.user.User;
import com.authobusy.domain.valueobject.PasswordValue;
import com.authobusy.endpoint.controller.password.request.PasswordChangeRequest;
import com.authobusy.repository.UsersRepository;

import com.authobusy.service.crypt.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UsersRepository usersRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UsersRepository usersRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.authobusy.domain.user.User userEntity = usersRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserDto(userEntity);
    }

    @Override
    public UserDto changePassword(PasswordChangeRequest request) {

        request.assertIsValid();

        com.authobusy.domain.user.User userEntity = usersRepository
                .findByEmail(request.getUsername());

        if (userEntity == null) {
            throw new UsernameNotFoundException(request.getUsername());
        }

        if (! this.passwordEncoder.matches(
                request.getOldPassword(),
                userEntity.getPassword().getValue())
        ) {
            throw new UsernameNotFoundException(request.getUsername());
        }

        String newPassword = this.passwordEncoder.encode(request.getNewPassword());

        User newUser = new User(
                userEntity.getEmail(),
                new PasswordValue(newPassword)
        );

        this.usersRepository.persist(newUser);

        return new UserDto(newUser);
    }
}