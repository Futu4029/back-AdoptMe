package com.unq.adopt_me.service;

import com.unq.adopt_me.common.GeneralResponse;

public interface AdoptionService {

    GeneralResponse getAdoptionsByOwnerId(String email);
    GeneralResponse searchAdoption(String type, String age, String size, String gender);
}
