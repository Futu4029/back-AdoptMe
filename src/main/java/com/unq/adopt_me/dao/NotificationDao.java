package com.unq.adopt_me.dao;

import com.unq.adopt_me.entity.notification.Notification;
import com.unq.adopt_me.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDao extends JpaRepository<Notification, Long> {
}
