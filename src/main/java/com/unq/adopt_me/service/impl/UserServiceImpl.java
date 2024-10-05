package com.unq.adopt_me.service.impl;

import com.unq.adopt_me.common.AbstractServiceResponse;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.dto.user.UserResponse;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.errorhandlers.BusinessException;
import com.unq.adopt_me.service.UserService;
import com.unq.adopt_me.common.GeneralResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl extends AbstractServiceResponse implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String ERROR_MESSAGE = "There was a problem getting the user, user not found.";
    public static final String SUCCESS_MESSAGE = "User obtained successfully";

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public GeneralResponse getUserProfile(String id) {
        try{
            logger.info("Getting user profile for user: [userId: {}]", id);
            User user = userDao.findById(Long.parseLong(id))
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));

            logger.info("SUCCESS - user successfully retrieved for [userId: {}]", id);
            return generateResponse(SUCCESS_MESSAGE, new UserResponse(user));
        }catch (BusinessException e){
            logger.error("ERROR - Getting user profile failed for user: [userId: {}] [errorMessage: {}]", id, e.getMessage());
            throw new BusinessException(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            logger.error("ERROR - Getting user profile failed for user: [userId: {}] [errorMessage: {}]", id, e.getMessage());
            throw new BusinessException("There was a problem getting the user for id: "+ id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public GeneralResponse createUser(User user) {
        // Encriptar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return generateResponse(SUCCESS_MESSAGE, userDao.save(user));
    }
}