package com.unq.adopt_me.util;

import com.unq.adopt_me.errorhandlers.BusinessException;
import org.springframework.http.HttpStatus;

public enum PetGender {
    MALE("Macho"),
    FEMALE("Hembra");

    private final String displayName;

    PetGender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static PetGender getEnum(String name) {
        for (PetGender size : PetGender.values()) {
            if (size.name().equalsIgnoreCase(name)) {
                return size;
            }
        }
        return null;
    }
}
