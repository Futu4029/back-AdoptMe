package com.unq.adopt_me.util.algoritmpointscalculator;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class AlgoritmPointsCalculator {

    public void calculateMatchPercentage(User userDto, AdoptionResponse response){

        Integer totalPoints = PointsPerAttribute.calculateReturnPoints(userDto, response);
        response.setMatchPoints(totalPoints);
    }
}
