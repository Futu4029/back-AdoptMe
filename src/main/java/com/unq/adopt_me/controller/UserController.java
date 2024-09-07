package com.unq.adopt_me.controller;

import com.unq.adopt_me.service.UserService;
import com.unq.adopt_me.util.GeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<GeneralResponse> getUserProfile(@PathVariable("email")String email){
        return ResponseEntity.ok(userService.getUserProfile(email));
    }
}
