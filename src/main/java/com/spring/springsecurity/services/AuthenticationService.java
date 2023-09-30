package com.spring.springsecurity.services;

import com.spring.springsecurity.models.User;
import com.spring.springsecurity.security.AuthenticationRequest;
import com.spring.springsecurity.security.AuthenticationResponse;

public interface AuthenticationService {
    public User create(User user);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public User update(User request);
    public Boolean recover(String username);
}
