package com.unq.adopt_me.controller;

import com.unq.adopt_me.common.AbstractController;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.adoption.ApplicationRequest;
import com.unq.adopt_me.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping()
    public GeneralResponse applyToAdoption(@Valid @RequestBody ApplicationRequest requestDto,
                                           @RequestHeader("Authorization") String authHeader) {
        requestDto.setUserId(getIdFromToken(authHeader));
        return applicationService.applyToAdoption(requestDto);
    }
}
