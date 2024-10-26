package com.unq.adopt_me.service.impl;

import com.unq.adopt_me.common.AbstractServiceResponse;
import com.unq.adopt_me.dao.RoleDao;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.dto.security.UserRegistrationDto;
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

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl extends AbstractServiceResponse implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String ERROR_MESSAGE = "There was a problem getting the user, user not found.";
    public static final String SUCCESS_GET_USER_MESSAGE = "User obtained successfully";
    public static final String SUCCESS_USER_REGISTERED_MESSAGE = "User registered successfully";
    public static final String ADMIN = "ADMIN";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public GeneralResponse getProfile(Long userId) {
        try{
            logger.info("Getting user profile for user: [userId: {}]", userId);
            User user = userDao.findById(userId)
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));

            logger.info("SUCCESS - user successfully retrieved for [userId: {}]", userId);
            return generateResponse(SUCCESS_GET_USER_MESSAGE, new UserResponse(user));
        }catch (BusinessException e){
            logger.error("ERROR - Getting user profile failed for user: [userId: {}] [errorMessage: {}]", userId, e.getMessage());
            throw new BusinessException(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            logger.error("ERROR - Getting user profile failed for user: [userId: {}] [errorMessage: {}]", userId, e.getMessage());
            throw new BusinessException("There was a problem getting the user for userId: "+ userId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public GeneralResponse createUser(User user) {
        // Encriptar la contraseña
        user.setRoles(Collections.singletonList(roleDao.findByName(ADMIN).get()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return generateResponse(SUCCESS_USER_REGISTERED_MESSAGE, userDao.save(user));
    }

    public GeneralResponse registerUser(UserRegistrationDto userRegistrationDto) {

        try{
            User newUser = new User(userRegistrationDto);
            newUser.setRoles(Collections.singletonList(roleDao.findByName(ADMIN).get()));
            newUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
            return generateResponse(SUCCESS_USER_REGISTERED_MESSAGE, userDao.save(newUser));
        }catch (Exception e){
            logger.error("ERROR - Register user failed: [errorMessage: {}]", e.getMessage());
            throw new BusinessException("There was a problem with registration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GeneralResponse getProfileByEmail(String email) {
        try{
            logger.info("Getting user profile for user: [email: {}]", email);
            User user = userDao.findByEmail(email)
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));

            logger.info("SUCCESS - user successfully retrieved for [email: {}]", email);
            return generateResponse(SUCCESS_GET_USER_MESSAGE, new UserResponse(user));
        }catch (BusinessException e){
            logger.error("ERROR - Getting user profile failed for user: [email: {}] [errorMessage: {}]", email, e.getMessage());
            throw new BusinessException(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            logger.error("ERROR - Getting user profile failed for user: [email: {}] [errorMessage: {}]", email, e.getMessage());
            throw new BusinessException("There was a problem getting the user for email: "+ email, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}