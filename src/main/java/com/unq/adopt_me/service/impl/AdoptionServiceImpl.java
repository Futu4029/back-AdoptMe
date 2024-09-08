package com.unq.adopt_me.service.impl;

import com.unq.adopt_me.dao.AdoptionDao;
import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.errorhandlers.BusinessException;
import com.unq.adopt_me.service.AdoptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdoptionServiceImpl implements AdoptionService {

    private static final Logger logger = LoggerFactory.getLogger(AdoptionServiceImpl.class);

    @Autowired
    private AdoptionDao adoptionDao;

    @Override
    public List<AdoptionResponse> getAdoptionsByOwnerId(String ownerId) {
        logger.info("Getting adoptions by user: [ownerId: {}]", ownerId);
        try {
            List<Adoption> adoptions = adoptionDao.findAllByOwner_Id(Long.parseLong(ownerId));
            List<AdoptionResponse> response = new ArrayList<>();
            if(!adoptions.isEmpty()){
                response = adoptions.stream().map(AdoptionResponse::new).collect(Collectors.toList());
            }
            return response;
        }catch (Exception e){
            logger.error("ERROR - Getting adoption failed for user: [userId: {}] [errorMessage: {}]", ownerId, e.getMessage());
            throw new BusinessException("There was a problem getting the adoption for userId: "+ ownerId, HttpStatus.NOT_FOUND);
        }
    }
}
