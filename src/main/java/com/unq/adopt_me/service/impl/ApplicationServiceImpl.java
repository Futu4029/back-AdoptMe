package com.unq.adopt_me.service.impl;

import com.unq.adopt_me.common.AbstractServiceResponse;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dao.AdoptionDao;
import com.unq.adopt_me.dao.ApplicationDao;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.dto.adoption.AdoptionInteractionRequest;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.adoption.Application;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.errorhandlers.BusinessException;
import com.unq.adopt_me.service.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.unq.adopt_me.service.impl.UserServiceImpl.ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl extends AbstractServiceResponse implements ApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(AdoptionServiceImpl.class);
    public static final String ERROR_MESSAGE_APPLICATION = "There was a problem applying to the adoption.";
    public static final String SUCCESS_CREATION_MESSAGE = "Application successfully created";
    public static final String SUCCESS_BLACKLIST_MESSAGE = "Application successfully blacklisted";

    @Autowired
    private AdoptionDao adoptionDao;

    @Autowired
    private UserDao userDao;


    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public GeneralResponse applyToAdoption(AdoptionInteractionRequest requestDto) {
        logger.info("CREATE APPLICATION - Applying to adoption process started for userId [userId: {}] and adoption [adoptionId: {}]", requestDto.getUserId(), requestDto.getAdoptionId());

        try{
            User user = userDao.findById(requestDto.getUserId())
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));
            Adoption adoption = adoptionDao.findById(UUID.fromString(requestDto.getAdoptionId()))
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE_APPLICATION, HttpStatus.NOT_FOUND));
            Application application = new Application(adoption, user);

            applicationDao.save(application);

            logger.info("CREATE APPLICATION - Applying to adoption process was successful for userId [userId: {}] and adoption [adoptionId: {}]", requestDto.getUserId(), requestDto.getAdoptionId());
            return generateResponse(SUCCESS_CREATION_MESSAGE, null);
        }catch (BusinessException e){
            logger.error("ERROR - Create application failed [errorMessage: {}]", e.getMessage());
            throw new BusinessException(e.getMessage(), e.getHttpStatus());
        }catch (DataIntegrityViolationException e){
            logger.error("ERROR - Create application failed, you can't apply for an adoption twice [errorMessage: {}]", e.getMessage());
            throw new BusinessException("There was a problem applying to the adoption, you can't apply for an adoption twice", HttpStatus.CONFLICT);
        }catch (Exception e){
            logger.error("ERROR - Create applicationn failed [errorMessage: {}]", e.getMessage());
            throw new BusinessException("There was a problem creating the adoption", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GeneralResponse blackListAdoption(AdoptionInteractionRequest requestDto) {
        logger.info("BLACKLISTING AN ADOPTION - Blacklisting to adoption process started for userId [userId: {}] and adoption [adoptionId: {}]", requestDto.getUserId(), requestDto.getAdoptionId());

        try{
            User user = userDao.findById(requestDto.getUserId())
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));
            user.getBlackList().add(UUID.fromString(requestDto.getAdoptionId()));

            userDao.save(user);

            logger.info("BLACKLISTING AN ADOPTION - Blacklisting to adoption process was successful for userId [userId: {}] and adoption [adoptionId: {}]", requestDto.getUserId(), requestDto.getAdoptionId());
            return generateResponse(SUCCESS_BLACKLIST_MESSAGE, null);
        }catch (BusinessException e){
            logger.error("ERROR - Blacklist adoption failed [errorMessage: {}]", e.getMessage());
            throw new BusinessException(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            logger.error("ERROR - Blacklist adoption failed [errorMessage: {}]", e.getMessage());
            throw new BusinessException("There was a problem blacklisting the adoption", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
