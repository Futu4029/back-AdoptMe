package com.unq.adopt_me.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.unq.adopt_me.common.AbstractServiceResponse;
import com.unq.adopt_me.dao.AdoptionDao;
import com.unq.adopt_me.dao.ApplicationDao;
import com.unq.adopt_me.dao.PetDao;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.dao.specifications.AdoptionSpecifications;
import com.unq.adopt_me.dto.adoption.AdoptionRequest;
import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.dto.adoption.OwnerInteractionRequest;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.adoption.Application;
import com.unq.adopt_me.entity.pet.Pet;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.errorhandlers.BusinessException;
import com.unq.adopt_me.security.CustomUserDetails;
import com.unq.adopt_me.service.AdoptionService;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.util.AdoptionStatus;
import com.unq.adopt_me.util.ApplicationStatus;
import com.unq.adopt_me.util.geolocalization.GeoCalculator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.unq.adopt_me.service.impl.UserServiceImpl.ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class AdoptionServiceImpl extends AbstractServiceResponse implements AdoptionService  {

    private static final Logger logger = LoggerFactory.getLogger(AdoptionServiceImpl.class);

    public static final String SUCCESS_SEARCH_MESSAGE = "Adoption successfully retrieved";
    public static final String SUCCESS_CREATION_MESSAGE = "Adoption successfully created";

    @Autowired
    private AdoptionDao adoptionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PetDao petDao;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private GeoCalculator geoCalculator;

    @Override
    public GeneralResponse getAdoptionsByOwnerId(Long ownerId) {
        logger.info("GET ADOPTION - Getting adoptions by user: [ownerId: {}]", ownerId);
        try {
            List<Adoption> adoptions = adoptionDao.findAllByOwner_Id(ownerId);
            List<AdoptionResponse> responseList = handleResponseList(adoptions);

            return generateResponse(SUCCESS_SEARCH_MESSAGE, responseList);
        }catch (Exception e){
            logger.error("ERROR - Getting adoption failed for user: [userId: {}] [errorMessage: {}]", ownerId, e.getMessage());
            throw new BusinessException("There was a problem getting the adoption for userId: "+ ownerId, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse getAdoptionsByEmail(String email) {
        logger.info("GET ADOPTION - Getting adoptions by [email: {}]", email);
        try {
            List<Adoption> adoptions = adoptionDao.findAllByOwnerEmail(email);
            List<AdoptionResponse> responseList = handleResponseList(adoptions);

            return generateResponse(SUCCESS_SEARCH_MESSAGE, responseList);
        }catch (Exception e){
            logger.error("ERROR - Getting adoption failed for user: [email: {}] [errorMessage: {}]", email, e.getMessage());
            throw new BusinessException("There was a problem getting the adoption for email: "+ email, HttpStatus.NOT_FOUND);
        }
    }

    private static List<AdoptionResponse> handleResponseList(List<Adoption> adoptions) {
        List<AdoptionResponse> responseList = new ArrayList<>();
        if(!adoptions.isEmpty()){
            responseList = adoptions.stream().map(AdoptionResponse::new).toList();
        }
        return responseList;
    }

    @Override
    public GeneralResponse searchAdoption(String type, String age, String size, String gender, String status, String distance) {
        logger.info("SEARCH ADOPTION - Searching adoptions with filter [type: {}] - [age: {}] - [size: {}] - [gender: {}] - [status: {}] - [distance: {}]", type, age, size, gender, status, distance);
        try {
            Pageable pageable = PageRequest.of(0, 50);
            // Get the specification based on the parameters
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDao.findById(customUserDetails.getUserId()).orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));
            Specification<Adoption> spec = AdoptionSpecifications.withFilters(type, age, size, gender, status, user);
            List<Adoption> adoptions = adoptionDao.findAll(spec, pageable).getContent();

            List<AdoptionResponse> responseList = handleResponseList(adoptions);

            responseList = addAndFilterByDistance(responseList, distance, user);

            logger.info(SUCCESS_SEARCH_MESSAGE + " sending elements [responseListQuantity: {}] ", responseList.size());

            return generateResponse(SUCCESS_SEARCH_MESSAGE, responseList);
        }catch (BusinessException e){
            logger.error("ERROR - Search adoption failed [errorMessage: {}]", e.getMessage());
            throw new BusinessException(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            logger.error("ERROR - Search adoption failed [errorMessage: {}]", e.getMessage());
            throw new BusinessException("There was a problem searching the adoption", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<AdoptionResponse> addAndFilterByDistance(List<AdoptionResponse> responseList, String distance, User user) {

        responseList = responseList.stream().filter(adoptionResponse -> {
            double distanceResponse = geoCalculator.calculateDistance(adoptionResponse.getOwner().getLocalization().getLatitude(),
                                            adoptionResponse.getOwner().getLocalization().getLongitude(),
                                            user.getLocalization().getLatitude(),
                                            user.getLocalization().getLongitude());
            adoptionResponse.setDistance(Math.round(distanceResponse * 100.0) / 100.0);
            return StringUtil.notNullNorEmpty(distance) ? distanceResponse < Double.parseDouble(distance) : true;
        }).toList();

        return responseList;
    }

    @Override
    public GeneralResponse createAdoption(AdoptionRequest requestDto) {
        logger.info("CREATE ADOPTION - Create adoption process started for pet [petName: {}] and user [userId: {}]", requestDto.getPetDto().getName(), requestDto.getUserId());

        try{
            Pet pet = new Pet(requestDto.getPetDto());
            User user = userDao.findById(requestDto.getUserId())
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));
            Adoption adoption = new Adoption(pet, user, AdoptionStatus.OPEN);
            petDao.save(pet);
            Adoption adoptionResponse = adoptionDao.save(adoption);
            logger.info("CREATE ADOPTION - Create adoption process was successful for pet [petName: {}] and user [userName: {}]", requestDto.getPetDto().getName(), user.getName());
            return generateResponse(SUCCESS_CREATION_MESSAGE, adoptionResponse);
        }catch (BusinessException e){
            logger.error("ERROR - Create adoption failed [errorMessage: {}]", e.getMessage());
            throw new BusinessException(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            logger.error("ERROR - Create adoption failed [errorMessage: {}]", e.getMessage());
            throw new BusinessException(e.getMessage() == null ? "There was a problem creating the adoption" : e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GeneralResponse ownerInteractionWithApplicants(OwnerInteractionRequest requestDto) {
        logger.info("OWNER INTERACTION - Create adoption process started for pet [adoptionId: {}] and user [status: {}]", requestDto.getAdoptionId(), requestDto.getStatus());

        try{
            Adoption adoption = adoptionDao.findById(UUID.fromString(requestDto.getAdoptionId()))
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));
            User adopter = userDao.findById(requestDto.getAdopterId())
                    .orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));
            Application application = applicationDao.getApplicationByAdopterAndAdoption(adopter, adoption);

            if(requestDto.getStatus()){
                adoption.setStatus(AdoptionStatus.APPROVED.getDisplayName());
                adoptionDao.save(adoption);
                application.setApplicationStatus(ApplicationStatus.APPROVED);
                applicationDao.save(application);
                List<Application> applicationList = applicationDao.getApplicationsByAdoption(adoption);
                applicationList.stream()
                        .filter(applicationFromList -> !applicationFromList.equals(application))
                        .forEach(applicationInList -> applicationDao.delete(applicationInList));
            }else{
                applicationDao.delete(application);
                adopter.getBlackList().add(adoption.getId());
            }

            logger.info("OWNER INTERACTION - Interacting with application process was successful for pet [adoptionId: {}] and user [adopterId: {}]", requestDto.getAdoptionId(), requestDto.getAdopterId());
            return generateResponse(SUCCESS_CREATION_MESSAGE, null);
        }catch (BusinessException e){
            logger.error("ERROR - Interacting with application failed [errorMessage: {}]", e.getMessage());
            throw new BusinessException(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            logger.error("ERROR - Interacting with application [errorMessage: {}]", e.getMessage());
            throw new BusinessException("There was a problem interacting with application", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public List<Adoption> filterAdoptions(List<Adoption> adoptionList){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(customUserDetails.getUserId()).orElseThrow(()-> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));
        //Filtro por los blacklisteados
        List<Adoption> filteredBlackList = adoptionList.stream().filter(adoption -> !user.getBlackList().contains(adoption.getId())).toList();
        //Filtro por los likeados
        return filteredBlackList.stream().filter(adoption -> !applicationDao.existsApplicationByAdopterAndAdoption(user, adoption)).toList();
    }
}
