package com.unq.adopt_me.service.impl;

import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.dto.user.UserResponse;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.errorhandlers.BusinessException;
import com.unq.adopt_me.service.UserService;
import com.unq.adopt_me.util.GeneralResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String ERROR_MESSAGE = "There was a problem getting the user, user not found.";
    public static final String SUCCESS_MESSAGE = "User obtained successfully";

    @Autowired
    private UserDao userDao;

    @Override
    public GeneralResponse getUserProfile(String id) {
        try{
            logger.info("Getting user profile for user: [userId: {}]", id);
            GeneralResponse response = new GeneralResponse();
            response.setMessage(SUCCESS_MESSAGE);
            User user = userDao.findById(Long.parseLong(id)).orElseThrow(()-> new BusinessException(ERROR_MESSAGE));
            response.setData(new UserResponse(user));
            logger.info("SUCCESS - user successfully retrieved for [userId: {}]", id);
            return response;
        }catch (Exception e){
            logger.error("ERROR - Getting user profile failed for user: [userId: {}] [errorMessage: {}]", id, e.getMessage());
            throw new BusinessException("There was a problem getting the user for id: "+ id, HttpStatus.NOT_FOUND);
        }
    }
}