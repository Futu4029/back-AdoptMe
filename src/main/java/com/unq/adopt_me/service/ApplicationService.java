package com.unq.adopt_me.service;

import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.adoption.AdoptionInteractionRequest;

public interface ApplicationService {

    GeneralResponse applyToAdoption(AdoptionInteractionRequest requestDto);
    GeneralResponse blackListAdoption(AdoptionInteractionRequest requestDto);

}
