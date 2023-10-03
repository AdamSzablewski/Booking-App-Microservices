package com.adamszablewski.security;

import com.adamszablewski.exceptions.InvalidCredentialsException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.security.dto.LoginDto;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void validateUser(LoginDto loginDto) {
        UserClass user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(NoSuchUserException::new);
        String hashedPassword = user.getPassword();
        if (!passwordEncoder.matches(loginDto.getPassword(), hashedPassword)){
            throw new InvalidCredentialsException();
        }
    }
}
