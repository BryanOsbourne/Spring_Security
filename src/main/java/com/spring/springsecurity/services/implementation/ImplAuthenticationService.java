package com.spring.springsecurity.services.implementation;

import com.spring.springsecurity.models.User;
import com.spring.springsecurity.repository.UserRepository;
import com.spring.springsecurity.security.AuthenticationRequest;
import com.spring.springsecurity.security.JwtService;
import com.spring.springsecurity.services.AuthenticationService;
import com.spring.springsecurity.security.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImplAuthenticationService implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public User create(User request) {
        if (request.getUUID() != null) {
            User user = userRepository.findById(request.getUUID()).orElse(null);
            String password = user.getPassword();
            user = request;
            user.setPassword(password);
            return userRepository.save(user);
        } else {
            request.setPassword(passwordEncoder.encode("12345"));
            return userRepository.save(request);
        }
    }
    @Override
    @Transactional
    public User update(User request) {
        User user = userRepository.findById(request.getUUID()).orElse(null);
        if (user != null) {
            if (request.getPassword() == null) {
                String password = user.getPassword();
                user = request;
                user.setPassword(password);
                return userRepository.save(user);
            } else {
                user = request;
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                return userRepository.save(user);
            }
        }
        return user;
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername());
        if (!user.isEnabled()) {
            return null;
        }
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .user(user)
                .build();
    }
    @Override
    @Transactional
    public Boolean recover(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(passwordEncoder.encode("12345"));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
