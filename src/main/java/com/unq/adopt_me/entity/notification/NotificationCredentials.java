package com.unq.adopt_me.entity.notification;

import com.unq.adopt_me.dto.notification.Keys;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NotificationCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String endpoint;
    private String expirateionTime;
    @Embedded
    private Keys keys;


    public NotificationCredentials(String endpoint, String expirateionTime, Keys keys) {
        this.endpoint = endpoint;
        this.expirateionTime = expirateionTime;
        this.keys = keys;
    }

}
