package com.unq.adopt_me.service.impl;

import com.unq.adopt_me.common.AbstractServiceResponse;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dao.AdoptionDao;
import com.unq.adopt_me.dao.ApplicationDao;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.dto.adoption.AdoptionInteractionRequest;
import com.unq.adopt_me.dto.application.ApplicationDto;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.adoption.Application;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.errorhandlers.BusinessException;
import com.unq.adopt_me.service.ApplicationService;
import com.unq.adopt_me.service.NotificationService;
import com.unq.adopt_me.util.ApplicationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.unq.adopt_me.service.impl.UserServiceImpl.ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl extends AbstractServiceResponse implements ApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);
    public static final String ERROR_MESSAGE_APPLICATION = "There was a problem applying to the adoption.";
    public static final String SUCCESS_CREATION_MESSAGE = "Application successfully created";
    public static final String SUCCESS_BLACKLIST_MESSAGE = "Application successfully blacklisted";
    public static final String SUCCESS_GET_MESSAGE = "Applications successfully obtained";
    public static final String ERROR_GETTING_APPLICATION_FAILED_ERROR_MESSAGE = "ERROR - Getting application failed [errorMessage: {}]";

    @Autowired
    private AdoptionDao adoptionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private NotificationService notificationService;

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
            Application application = new Application(adoption, user, ApplicationStatus.PENDING);

            applicationDao.save(application);

            logger.info("CREATE APPLICATION - Applying to adoption process was successful for userId [userId: {}] and adoption [adoptionId: {}]", requestDto.getUserId(), requestDto.getAdoptionId());
            notificationService.sendNotification("Applying to adoption process was successful for userId");
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

    @Override
    public GeneralResponse getApplicationByUserId(Long userId) {
        logger.info("GETTING APPLICATIONS BY USER - Getting applications by [userId: {}]", userId);

        try{
            User user = userDao.findById(userId)
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));

            List<Application> applicationList = applicationDao.getApplicationsByAdopter(user);

            List<ApplicationDto> applicationDtoResponse = applicationList.stream().map(ApplicationDto::new).toList();

            logger.info("GETTING APPLICATIONS BY USER - Applications obtained successfully [userId: {}] found: [applicationQuantity: {}]", userId, applicationList.size());
            return generateResponse(SUCCESS_GET_MESSAGE, applicationDtoResponse);
        }catch (BusinessException e){
            logger.error(ERROR_GETTING_APPLICATION_FAILED_ERROR_MESSAGE, e.getMessage());
            throw new BusinessException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public GeneralResponse getApplicationByAdoption(String adoptionInteractionRequest) {
        logger.info("GETTING APPLICATIONS BY ADOPTION - Getting applications by [adoptionId: {}]", adoptionInteractionRequest);
        try{
            Adoption adoption = adoptionDao.findById(UUID.fromString(adoptionInteractionRequest))
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));
            List<Application> applicationList = applicationDao.getApplicationsByAdoption(adoption);

            List<ApplicationDto> applicationDtoResponse = applicationList.stream().map(ApplicationDto::new).toList();

            logger.info("GETTING APPLICATIONS BY ADOPTION - Applications obtained successfully [adoptionId: {}] found: [applicationQuantity: {}]", adoptionInteractionRequest, applicationList.size());
            return generateResponse(SUCCESS_GET_MESSAGE, applicationDtoResponse);
        }catch (BusinessException e){
            logger.error(ERROR_GETTING_APPLICATION_FAILED_ERROR_MESSAGE, e.getMessage());
            throw new BusinessException(e.getMessage(), e.getHttpStatus());
        }
    }


}
