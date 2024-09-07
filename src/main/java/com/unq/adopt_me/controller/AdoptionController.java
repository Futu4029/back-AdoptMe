package com.unq.adopt_me.controller;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.service.AdoptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/adoption")
public class AdoptionController {

    @Autowired
    private AdoptionService adoptionService;

    @GetMapping("/{ownerId}")
    public ResponseEntity<List<AdoptionResponse>> getAdoptionsByOwnerId(@PathVariable("ownerId")String ownerId){
        return ResponseEntity.ok(adoptionService.getAdoptionsByOwnerId(ownerId));
    }
}
