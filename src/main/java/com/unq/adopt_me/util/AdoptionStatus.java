package com.unq.adopt_me.util;

public enum AdoptionStatus {
    OPEN("Open"),
    PENDING("Pending"),
    APPROVED("Approved");

    private final String displayName;

    AdoptionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static AdoptionStatus getEnum(String name) {
        for (AdoptionStatus value : AdoptionStatus.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}