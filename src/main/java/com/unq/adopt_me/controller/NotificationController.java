package com.unq.adopt_me.controller;

import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dto.notification.SubscriptionRequest;
import com.unq.adopt_me.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/save")
    public GeneralResponse saveSubscription(@RequestBody SubscriptionRequest subscriptionRequest) {
        return notificationService.saveToken(subscriptionRequest);
    }

}
