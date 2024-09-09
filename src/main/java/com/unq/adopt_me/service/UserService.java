package com.unq.adopt_me.service;

import com.unq.adopt_me.common.GeneralResponse;

public interface UserService {

    GeneralResponse getUserProfile(String email);
}
