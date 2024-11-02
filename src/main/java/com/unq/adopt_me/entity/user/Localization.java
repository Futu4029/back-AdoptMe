package com.unq.adopt_me.entity.user;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Localization {

    private String longitude;
    private String latitude;

    public Localization(String longitud, String latitud) {
        this.longitude = longitud;
        this.latitude = latitud;
    }

    public Localization() {
    }
}
