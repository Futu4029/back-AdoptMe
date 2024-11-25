package com.unq.adopt_me.util.algoritmpointscalculator.pointscalculator;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.util.algoritmpointscalculator.PointsPerAttribute;

public interface PointsCalculator {

    boolean canHandle(PointsPerAttribute attribute);
    int calculatePoints(AdoptionResponse adoptionResponse, User user);
}
