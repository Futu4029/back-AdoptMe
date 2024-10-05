package com.unq.adopt_me.service;

import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.adoption.AdoptionRequest;

public interface AdoptionService {

    GeneralResponse getAdoptionsByOwnerId(String id);
    GeneralResponse getAdoptionsByEmail(String email);
    GeneralResponse searchAdoption(String type, String age, String size, String gender);
    GeneralResponse createAdoption(AdoptionRequest requestDto);
}
