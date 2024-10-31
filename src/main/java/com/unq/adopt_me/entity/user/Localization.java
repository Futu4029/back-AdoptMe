package com.unq.adopt_me.entity.user;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Localization {

    private String longitud;
    private String latitud;

    public Localization(String longitud, String latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public Localization() {
    }
}
