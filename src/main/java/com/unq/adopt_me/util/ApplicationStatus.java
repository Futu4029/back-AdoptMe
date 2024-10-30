package com.unq.adopt_me.util;

public enum ApplicationStatus {
    DENIED("Denied"),
    PENDING("Pending"),
    APPROVED("Approved");

    private final String displayName;

    ApplicationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ApplicationStatus getEnum(String name) {
        for (ApplicationStatus value : ApplicationStatus.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}
