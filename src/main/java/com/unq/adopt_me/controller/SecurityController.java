package com.unq.adopt_me.controller;

import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.security.LoginDto;
import com.unq.adopt_me.dto.security.UserRegistrationDto;
import com.unq.adopt_me.service.SecurityService;
import com.unq.adopt_me.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public GeneralResponse login(@Valid @RequestBody LoginDto loginDto) {
        return securityService.validate(loginDto);
    }
    @GetMapping("/isvalid")
    public GeneralResponse isValidToken(@RequestHeader("Authorization") String authHeader) {
        return securityService.isValid(authHeader);
    }
    @PostMapping("/register")
    public GeneralResponse registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        return userService.registerUser(userRegistrationDto);
    }
}
