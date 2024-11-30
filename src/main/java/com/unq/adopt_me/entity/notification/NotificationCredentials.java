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
    private String expirationTime;
    @Embedded
    private Keys keys;
    @Column(nullable = false, unique = true)
    private Long userId;


    public NotificationCredentials(String endpoint, String expirationTime, Keys keys, Long userId) {
        this.endpoint = endpoint;
        this.expirationTime = expirationTime;
        this.keys = keys;
        this.userId = userId;
    }

}
