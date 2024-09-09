package com.unq.adopt_me.util;

import com.unq.adopt_me.errorhandlers.BusinessException;
import org.springframework.http.HttpStatus;

public enum PetSize {
    SMALL("Pequeño"),
    MEDIUM("Mediano"),
    LARGE("Grande");

    private final String displayName;

    PetSize(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static PetSize getEnum(String name) {
        for (PetSize size : PetSize.values()) {
            if (size.name().equalsIgnoreCase(name)) {
                return size;
            }
        }
        throw new BusinessException("No size found for parameter: " + name, HttpStatus.BAD_REQUEST);
    }
}
