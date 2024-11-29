package com.unq.adopt_me.util.algoritmpointscalculator.pointscalculator;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.util.PetAge;
import com.unq.adopt_me.util.algoritmpointscalculator.PointsPerAttribute;

public class PetAgePointsCalculator implements PointsCalculator {
    @Override
    public boolean canHandle(PointsPerAttribute attribute) {
        return attribute == PointsPerAttribute.PET_AGE;
    }

    @Override
    public int calculatePoints(AdoptionResponse adoptionResponse, User user) {
        PetAge petAge = PetAge.getPetAgeFromAge(adoptionResponse.getPet().getAge());
        assert petAge != null;
        return PointsPerAttribute.calculatePetAgePoints(petAge);
    }
}