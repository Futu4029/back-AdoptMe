package com.unq.adopt_me.util;

import com.unq.adopt_me.errorhandlers.BusinessException;
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

    public boolean isInRange(int age) {
        return age >= minAge && (maxAge == null || age <= maxAge);
    }

    public static PetAge getEnum(String name) {
        for (PetAge filter : com.unq.adopt_me.util.PetAge.values()) {
            if (filter.name().equalsIgnoreCase(name)) {
                return filter;
            }
        }
        throw new BusinessException("No age found for parameter: " + name, HttpStatus.BAD_REQUEST);
    }

    // Método para obtener el PetAge según la edad proporcionada
    public static PetAge fromAge(int age) {
        for (PetAge filter : com.unq.adopt_me.util.PetAge.values()) {
            if (age >= filter.getMinAge() && age <= filter.getMaxAge()) {
                return filter;
            }
        }
        throw new BusinessException("No age found for parameter: " + age, HttpStatus.BAD_REQUEST);
    }
}
