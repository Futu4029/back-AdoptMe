package com.unq.adopt_me.service;

import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.security.LoginDto;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {


    GeneralResponse validate(LoginDto loginDto);
    GeneralResponse isValid(String authHeader);
}
