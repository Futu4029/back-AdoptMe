package com.unq.adopt_me.util.algoritmpointscalculator;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.dto.user.UserDto;
import com.unq.adopt_me.util.PetSize;
import com.unq.adopt_me.util.PetAge;

public enum PointsPerAttribute {

    DISTANCE(30,10),
    PET_AGE(20, 10),
    PET_SIZE(20,10);

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

    public static Integer calculateReturnPoints(AdoptionResponse adoptionResponse, UserDto userDto, PointsPerAttribute pointsPerAttribute) {
        switch (pointsPerAttribute){
            case DISTANCE:
                double distance = Math.abs(adoptionResponse.getDistance()- userDto.getDistance());
                if(distance < 20){
                    return DISTANCE.maxPoints;
                }else if(distance > 20 && distance < 50) {
                    return DISTANCE.maxPoints / 2;
                }else{
                    return DISTANCE.minPoints;
                }
            case PET_AGE:
                PetAge petAge = PetAge.getPetAgeFromAge(adoptionResponse.getPet().getAge());
                return switch (petAge) {
                    case ADULT -> PET_AGE.maxPoints;
                    case YOUNG -> PET_AGE.maxPoints / 2;
                    case PUPPY -> PET_AGE.minPoints;
                };
            case PET_SIZE:
                PetSize petSize = PetSize.getEnum(adoptionResponse.getPet().getSize());
                return calculatePetSizePoints(petSize, userDto);
            default:
                return 0;
        }
    }

    public static Integer calculatePetSizePoints(PetSize petSize, UserDto userDto) {
        switch (petSize) {
            case SMALL:
                return PET_SIZE.maxPoints;

            case MEDIUM:
                if(userDto.getLivesOnHouse()){
                    return PET_SIZE.maxPoints;
                }else {
                    return PET_SIZE.maxPoints / 2;
                }
            case LARGE:
                if(userDto.getLivesOnHouse()){
                    return PET_SIZE.maxPoints;
                }else {
                    return PET_SIZE.minPoints;
                }
            default:
                return PET_SIZE.minPoints;
        }
    }
}
