package com.unq.adopt_me.service.impl;

import com.unq.adopt_me.common.AbstractServiceResponse;
import com.unq.adopt_me.dao.AdoptionDao;
import com.unq.adopt_me.dao.PetDao;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.dao.specifications.AdoptionSpecifications;
import com.unq.adopt_me.dto.adoption.AdoptionRequest;
import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.pet.Pet;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.errorhandlers.BusinessException;
import com.unq.adopt_me.service.AdoptionService;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.util.AdoptionStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public GeneralResponse getAdoptionsByOwnerId(String ownerId) {
        logger.info("GET ADOPTION - Getting adoptions by user: [ownerId: {}]", ownerId);
        try {
            List<Adoption> adoptions = adoptionDao.findAllByOwner_Id(Long.parseLong(ownerId));
            List<AdoptionResponse> responseList = new ArrayList<>();
            if(!adoptions.isEmpty()){
                responseList = adoptions.stream().map(AdoptionResponse::new).collect(Collectors.toList());
            }

            return generateResponse(SUCCESS_SEARCH_MESSAGE, responseList);
        }catch (Exception e){
            logger.error("ERROR - Getting adoption failed for user: [userId: {}] [errorMessage: {}]", ownerId, e.getMessage());
            throw new BusinessException("There was a problem getting the adoption for userId: "+ ownerId, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse searchAdoption(String type, String age, String size, String gender) {
        logger.info("SEARCH ADOPTION - Searching adoptions with filter [type: {}] - [age: {}] - [size: {}] - [gender: {}]", type, age, size, gender);
        try {
            Pageable pageable = PageRequest.of(0, 50);
            // Get the specification based on the parameters
            Specification<Adoption> spec = AdoptionSpecifications.withFilters(type, age, size, gender);
            List<Adoption> adoptions = adoptionDao.findAll(spec, pageable).getContent();

            List<AdoptionResponse> responseList = new ArrayList<>();
            if(!adoptions.isEmpty()){
                responseList = adoptions.stream().map(AdoptionResponse::new).collect(Collectors.toList());
            }
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

    @Override
    public GeneralResponse createAdoption(AdoptionRequest requestDto) {
        logger.info("CREATE ADOPTION - Create adoption process started for pet [petName: {}] and user [userId: {}]", requestDto.getPetDto().getName(), requestDto.getUserId());

        try{

            Pet pet = new Pet(requestDto.getPetDto());
            User user = userDao.findById(Long.parseLong(requestDto.getUserId()))
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
            throw new BusinessException("There was a problem creating the adoption", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
