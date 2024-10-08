package com.unq.adopt_me.service;

import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.adoption.ApplicationRequest;

public interface ApplicationService {

    GeneralResponse applyToAdoption(ApplicationRequest requestDto);

}
