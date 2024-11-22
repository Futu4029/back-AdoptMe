package com.unq.adopt_me.util;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.errorhandlers.BusinessException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.http.HttpStatus;

public enum PetAge {
    PUPPY(0, 2),
    YOUNG(3, 5),
    ADULT(6, Integer.MAX_VALUE); // Usar Integer.MAX_VALUE para un rango abierto

    private final int minAge;
    private final Integer maxAge;

    PetAge(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public static PetAge getPetAgeFromAge(Integer age) {
        for (PetAge petAge : PetAge.values()) {
            if(petAge.getMinAge() <= age && age <= petAge.getMaxAge()) {
                return petAge;
            }
        }
        return null;
    }

    public static PetAge getEnum(String name) {
        for (PetAge filter : com.unq.adopt_me.util.PetAge.values()) {
            if (filter.name().equalsIgnoreCase(name)) {
                return filter;
            }
        }
        return null;
    }

}
