package com.authobusy.service.access;

import com.authobusy.domain.user.User;
import com.authobusy.domain.valueobject.EncryptedPasswordValue;
import com.authobusy.domain.valueobject.PlainPasswordValue;
import com.authobusy.service.access.request.PasswordChangeRequest;
import com.authobusy.domain.user.UsersRepository;

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

        // To Value object, it validates in constructor
        PlainPasswordValue newPassword = new PlainPasswordValue(request.getNewPassword());

        com.authobusy.domain.user.User userEntity = usersRepository
            .findByEmail(request.getUsername());

        if (userEntity == null) {
            throw new UsernameNotFoundException(request.getUsername());
        }

        if (!this.passwordEncoder.matches(
                request.getOldPassword(),
                userEntity.getPassword().getValue())
        ) {
            throw new UsernameNotFoundException(request.getUsername());
        }

        String newPasswordEnc = this.passwordEncoder.encode(newPassword.getValue());

        User newUser = new User(
            userEntity.getEmail(),
            new EncryptedPasswordValue(newPasswordEnc)
        );

        this.usersRepository.persist(newUser);

        return new UserDto(newUser);
    }
}