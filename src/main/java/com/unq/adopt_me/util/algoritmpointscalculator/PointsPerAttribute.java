package com.unq.adopt_me.util.algoritmpointscalculator;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.util.PetSize;
import com.unq.adopt_me.util.PetAge;

public enum PointsPerAttribute {

    DISTANCE(30,10),
    PET_AGE(20, 5),
    PET_SIZE(15,5);

    private final Integer maxPoints;
    private final Integer minPoints;

    PointsPerAttribute(Integer maxPoints, Integer minPoints) {
        this.maxPoints = maxPoints;
        this.minPoints = minPoints;
    }

    public static PointsPerAttribute getEnum(String name) {
        for (PointsPerAttribute value : PointsPerAttribute.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public static Integer calculateReturnPoints(AdoptionResponse adoptionResponse, User user) {
        Integer returnPoints = 0;
        double distance = adoptionResponse.getDistance();
        if(distance < 20){
            returnPoints += DISTANCE.maxPoints;
        }else if(distance > 20 && distance < 50) {
            returnPoints +=  DISTANCE.maxPoints / 2;
        }else{
            returnPoints +=  DISTANCE.minPoints;
        }

        PetAge petAge = PetAge.getPetAgeFromAge(adoptionResponse.getPet().getAge());
        switch (petAge) {
            case ADULT -> returnPoints += PET_AGE.maxPoints;
            case YOUNG -> returnPoints += PET_AGE.maxPoints / 2;
            case PUPPY -> returnPoints += PET_AGE.minPoints;
            };

        PetSize petSize = PetSize.getEnum(adoptionResponse.getPet().getSize());
        returnPoints += calculatePetSizePoints(petSize, user);

        return returnPoints;
    }

    public static Integer calculatePetSizePoints(PetSize petSize, User user) {
        switch (petSize) {
            case SMALL:
                return PET_SIZE.maxPoints;
            case MEDIUM:
                if(user.getLivesOnHouse()){
                    return PET_SIZE.maxPoints;
                }else {
                    return PET_SIZE.maxPoints / 2;
                }
            case LARGE:
                if(user.getLivesOnHouse()){
                    return PET_SIZE.maxPoints;
                }else {
                    return PET_SIZE.minPoints;
                }
            default:
                return PET_SIZE.minPoints;
        }
    }
}
