package com.unq.adopt_me.entity.user;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Localization {

    private Double longitude;
    private Double latitude;

    public Localization(Double longitud, Double latitud) {
        this.longitude = longitud;
        this.latitude = latitud;
    }

    public Localization() {
    }
}
