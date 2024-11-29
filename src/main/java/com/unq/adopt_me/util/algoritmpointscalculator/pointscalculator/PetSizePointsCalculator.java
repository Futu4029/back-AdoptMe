package com.unq.adopt_me.util.algoritmpointscalculator.pointscalculator;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.util.PetSize;
import com.unq.adopt_me.util.algoritmpointscalculator.PointsPerAttribute;

public class PetSizePointsCalculator implements PointsCalculator {
    @Override
    public boolean canHandle(PointsPerAttribute attribute) {
        return attribute == PointsPerAttribute.PET_SIZE;
    }

    @Override
    public int calculatePoints(AdoptionResponse adoptionResponse, User user) {
        PetSize petSize = PetSize.getEnum(adoptionResponse.getPet().getSize());
        assert petSize != null;
        return PointsPerAttribute.calculatePetSizePoints(petSize, user.getLivesOnHouse());
    }
}
