package com.unq.adopt_me.controller;

import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.security.LoginDto;
import com.unq.adopt_me.entity.user.User;
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

    @PostMapping("/login")
    public GeneralResponse login(@Valid @RequestBody LoginDto loginDto) {
        return securityService.validate(loginDto);
    }
}
