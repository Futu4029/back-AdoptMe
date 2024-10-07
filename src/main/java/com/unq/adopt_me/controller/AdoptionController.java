package com.unq.adopt_me.controller;

import com.unq.adopt_me.common.AbstractController;
import com.unq.adopt_me.dto.adoption.AdoptionRequest;
import com.unq.adopt_me.service.AdoptionService;
import com.unq.adopt_me.common.GeneralResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/adoption")
public class AdoptionController extends AbstractController {

    @Autowired
    private AdoptionService adoptionService;

    @GetMapping
    public GeneralResponse getAdoptionsByOwnerId(@RequestHeader("Authorization") String authHeader){
        return adoptionService.getAdoptionsByOwnerId(getIdFromToken(authHeader));
    }

    @GetMapping("/search/by-email/{email}")
    public GeneralResponse getAdoptionsByEmail(@PathVariable("email") String email){
        return adoptionService.getAdoptionsByEmail(email);
    }

    @GetMapping("/search")
    public GeneralResponse searchAdoptions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String age,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String status) {
        return adoptionService.searchAdoption(type, age, size, gender, status);
    }

    @PostMapping
    public GeneralResponse createAdoption(@Valid @RequestBody AdoptionRequest requestDto,
                                          @RequestHeader("Authorization") String authHeader) {
        requestDto.setUserId(getIdFromToken(authHeader));
        return adoptionService.createAdoption(requestDto);
    }
}
