package com.unq.adopt_me.util;

import com.unq.adopt_me.errorhandlers.BusinessException;
import org.springframework.http.HttpStatus;

public enum PetType {
    DOG("Perro"),
    CAT("Gato");

    private final String displayName;

    PetType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PetType getEnum(String name) {
        for (PetType type : PetType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
