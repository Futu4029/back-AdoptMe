package com.unq.adopt_me.controller;

import com.unq.adopt_me.common.AbstractController;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.service.UserService;
import com.unq.adopt_me.common.GeneralResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<GeneralResponse> getProfile(@RequestHeader("Authorization") String authHeader){
        return ResponseEntity.ok(userService.getProfile(getIdFromToken(authHeader)));
    }

    @PostMapping("/register")
    public GeneralResponse registerUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }
}
