package com.unq.adopt_me.service;

import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.adoption.AdoptionRequest;
import com.unq.adopt_me.dto.adoption.OwnerInteractionRequest;

public interface AdoptionService {

    GeneralResponse getAdoptionsByOwnerId(Long id);
    GeneralResponse getAdoptionsByEmail(String email);
    GeneralResponse searchAdoption(String type, String age, String size, String gender, String status);
    GeneralResponse createAdoption(AdoptionRequest requestDto);
    GeneralResponse ownerInteractionWithApplicants(OwnerInteractionRequest requestDto);
}
