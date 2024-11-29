package com.unq.adopt_me.service;

import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.notification.SubscriptionRequest;

public interface NotificationService {

    GeneralResponse saveToken(SubscriptionRequest token);
    void sendNotification(String message, Long userId);
}
