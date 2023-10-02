package com.spring.springsecurity.controllers;

import com.spring.springsecurity.models.User;
import com.spring.springsecurity.security.AuthenticationRequest;
import com.spring.springsecurity.services.AuthenticationService;
import com.spring.springsecurity.security.AuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v6/springSecurity/authentication")
public class AuthenticationController { 
    
    @Autowired
    private AuthenticationService authenticationService;
    
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
   
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            return new ResponseEntity<>(authenticationService.create(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/update")
    public ResponseEntity<User> update(@RequestBody User request) {
        try {
            return new ResponseEntity<>(authenticationService.update(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/recover")
    public ResponseEntity<Boolean> recover(@RequestBody String username) {
        try {
            return new ResponseEntity<>(authenticationService.recover(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
