package com.unq.adopt_me.service.impl;

import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.service.PetService;
import com.unq.adopt_me.util.GeneralResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PetServiceImpl implements PetService {
    @Autowired
    private UserDao userDao;

    @Override
    public GeneralResponse getPetProfile(String id) {
        return null;
    }
}
