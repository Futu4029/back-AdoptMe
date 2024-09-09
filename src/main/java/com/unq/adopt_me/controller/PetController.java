package com.unq.adopt_me.controller;

import com.unq.adopt_me.service.PetService;
import com.unq.adopt_me.common.GeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    /*@GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getUserProfile(@PathVariable("id")String id){
        return ResponseEntity.ok(petService.getPetProfile(id));
    }*/
}
