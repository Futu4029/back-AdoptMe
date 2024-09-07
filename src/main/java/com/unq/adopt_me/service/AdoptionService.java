package com.unq.adopt_me.service;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;

import java.util.List;

public interface AdoptionService {

    List<AdoptionResponse> getAdoptionsByOwnerId(String email);
}
