package com.spring.springsecurity.services;

import com.spring.springsecurity.models.User;
import java.util.List;

public interface UserService {
    public User findByUsername(String username);
    public List<User> findAll();
}
