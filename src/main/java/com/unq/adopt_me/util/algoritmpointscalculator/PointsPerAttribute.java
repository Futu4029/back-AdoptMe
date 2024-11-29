package com.unq.adopt_me.util.algoritmpointscalculator;

import com.unq.adopt_me.util.PetSize;
import com.unq.adopt_me.util.PetAge;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.unq.adopt_me.util.PetAge.*;

public enum PointsPerAttribute {

    DISTANCE(50, 25 ,15),
    PET_AGE(30, 15, 10),
    PET_SIZE(20, 15,10);

    private final Integer maxPoints;
    private final Integer midPoints;
    private final Integer minPoints;

    static final Map<Object, Integer> petAgeMap = new HashMap<>()
    {
        {
            put(ADULT, PET_AGE.maxPoints);
            put(YOUNG, PET_AGE.midPoints);
            put(PUPPY, PET_AGE.minPoints);
        }
    };

    static final Map<PetSize, Function<Boolean, Integer>> petSizeMap = new HashMap<>() {
        {
            put(PetSize.SMALL, livesOnHouse -> PointsPerAttribute.PET_SIZE.maxPoints);
            put(PetSize.MEDIUM, livesOnHouse -> livesOnHouse ? PointsPerAttribute.PET_SIZE.maxPoints : PointsPerAttribute.PET_SIZE.midPoints);
            put(PetSize.LARGE, livesOnHouse -> livesOnHouse ? PointsPerAttribute.PET_SIZE.maxPoints : PointsPerAttribute.PET_SIZE.minPoints);
        }
    };


    PointsPerAttribute(Integer maxPoints, Integer midPoints, Integer minPoints) {
        this.maxPoints = maxPoints;
        this.midPoints = midPoints;
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

    public static Integer calculateDistancePoints(double distance) {
        if(distance < 20){
            return DISTANCE.maxPoints;
        }else if(distance > 20 && distance < 50) {
            return DISTANCE.midPoints;
        }else{
            return DISTANCE.minPoints;
        }
    }

    public static Integer calculatePetAgePoints(PetAge petAge) {
        return petAgeMap.get(petAge);
    }

    public static Integer calculatePetSizePoints(PetSize petSize, Boolean livesOnHouse) {
        return petSizeMap.getOrDefault(petSize, house -> PointsPerAttribute.PET_SIZE.minPoints).apply(livesOnHouse);
    }
}
