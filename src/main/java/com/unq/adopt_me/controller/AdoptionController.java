package com.unq.adopt_me.controller;

import com.unq.adopt_me.service.AdoptionService;
import com.unq.adopt_me.common.GeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/adoption")
public class AdoptionController {

    @Autowired
    private AdoptionService adoptionService;

    @GetMapping("/{ownerId}")
    public GeneralResponse getAdoptionsByOwnerId(@PathVariable("ownerId")String ownerId){
        return adoptionService.getAdoptionsByOwnerId(ownerId);
    }

    @GetMapping("/search")
    public GeneralResponse searchAdoptions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String age,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String gender) {
        return adoptionService.searchAdoption(type, age, size, gender);
    }
}
