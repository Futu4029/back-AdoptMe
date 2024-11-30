package com.unq.adopt_me.dao;

import com.unq.adopt_me.dto.notification.Keys;
import com.unq.adopt_me.entity.notification.NotificationCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDao extends JpaRepository<NotificationCredentials, Long> {
    NotificationCredentials findByUserId(Long userId);

    @Modifying
    @Query(value = """
    INSERT INTO notification (endpoint, expiration_time, user_id, p256dh, auth)
    VALUES (:endpoint, :expirationTime, :userId, :p256dh, :auth)
    ON DUPLICATE KEY UPDATE 
        endpoint = VALUES(endpoint), 
        expiration_time = VALUES(expiration_time),
        p256dh = VALUES(p256dh),
        auth = VALUES(auth)
    """, nativeQuery = true)
    void upsert(
            @Param("endpoint") String endpoint,
            @Param("expirationTime") String expirationTime,
            @Param("userId") Long userId,
            @Param("p256dh") String p256dh,
            @Param("auth") String auth
    );
}
