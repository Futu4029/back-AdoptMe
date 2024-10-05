package com.unq.adopt_me.service.impl;

import com.unq.adopt_me.common.AbstractServiceResponse;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dao.RoleDao;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.dto.security.AuthResponse;
import com.unq.adopt_me.dto.security.LoginDto;
import com.unq.adopt_me.dto.user.UserResponse;
import com.unq.adopt_me.security.JWTTokenProvider;
import com.unq.adopt_me.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SecurityServiceImpl extends AbstractServiceResponse implements SecurityService {


    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
    public static final String SUCCESS_MESSAGE = "User logged successfully";

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private RoleDao roleDao;
    private UserDao userDao;
    private JWTTokenProvider tokenProvider;

    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleDao roleDao, UserDao userDao, JWTTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public GeneralResponse validate(LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generarToken(authentication);

        logger.info("SUCCESS - user successfully loged [email: {}]", loginDto.getEmail());
        return generateResponse(SUCCESS_MESSAGE, new AuthResponse(token));
    }
}
