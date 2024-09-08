package com.unq.adopt_me.util;

public enum AdoptionStatus {
    OPEN("Open"),
    PENDING("Pending"),
    APPROVED("Approved");

    private final String value;

    // Constructor
    AdoptionStatus(String value) {
        this.value = value;
    }

    // Getter para obtener el valor String
    public String getValue() {
        return value;
    }
}