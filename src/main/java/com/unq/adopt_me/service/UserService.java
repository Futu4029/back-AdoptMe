package com.unq.adopt_me.service;

import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.security.UserRegistrationDto;
import com.unq.adopt_me.entity.user.User;

public interface UserService {

    GeneralResponse getProfile(Long id);
    GeneralResponse createUser(User user);
    GeneralResponse registerUser(UserRegistrationDto userRegistrationDto);

}
