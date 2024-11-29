package com.unq.adopt_me.dao;

import com.unq.adopt_me.entity.notification.NotificationCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDao extends JpaRepository<NotificationCredentials, Long> {
    NotificationCredentials findByUserId(Long userId);
}
