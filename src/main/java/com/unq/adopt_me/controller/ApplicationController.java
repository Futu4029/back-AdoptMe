package com.unq.adopt_me.controller;

import com.unq.adopt_me.common.AbstractController;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.adoption.AdoptionInteractionRequest;
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
    public GeneralResponse applyToAdoption(@Valid @RequestBody AdoptionInteractionRequest requestDto,
                                           @RequestHeader("Authorization") String authHeader) {
        requestDto.setUserId(getIdFromToken(authHeader));
        return applicationService.applyToAdoption(requestDto);
    }

    @PutMapping()
    public GeneralResponse blackListAdoption(@Valid @RequestBody AdoptionInteractionRequest requestDto,
                                           @RequestHeader("Authorization") String authHeader) {
        requestDto.setUserId(getIdFromToken(authHeader));
        return applicationService.blackListAdoption(requestDto);
    }

    @GetMapping("/user")
    public GeneralResponse getApplicationsByUserId(@RequestHeader("Authorization") String authHeader) {
        return applicationService.getApplicationByUserId(getIdFromToken(authHeader));
    }

    @GetMapping("/adoption")
    public GeneralResponse getApplicationsByAdoption(@Valid @RequestBody AdoptionInteractionRequest requestDto) {
        return applicationService.getApplicationByAdoption(requestDto);
    }
}
