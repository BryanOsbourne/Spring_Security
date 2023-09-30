package com.spring.springsecurity.services.implementation;

import com.spring.springsecurity.models.User;
import com.spring.springsecurity.repository.UserRepository;
import com.spring.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImplUserService implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
