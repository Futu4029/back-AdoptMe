package com.unq.adopt_me.util.algoritmpointscalculator.pointscalculator;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.util.algoritmpointscalculator.PointsPerAttribute;

public class DistancePointsCalculator implements PointsCalculator {
    @Override
    public boolean canHandle(PointsPerAttribute attribute) {
        return attribute == PointsPerAttribute.DISTANCE;
    }

    @Override
    public int calculatePoints(AdoptionResponse adoptionResponse, User user) {
        double distance = adoptionResponse.getDistance();
        return PointsPerAttribute.calculateDistancePoints(distance);
    }
}