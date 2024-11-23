package com.unq.adopt_me.controller;

import com.unq.adopt_me.dto.notification.SubscriptionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @PostMapping("/save")
    public ResponseEntity<String> saveSubscription(@RequestBody SubscriptionRequest subscription) {
        // Aquí guardas el token en la base de datos
        System.out.println("Token recibido: " + subscription.getToken());
        return ResponseEntity.ok("Token guardado correctamente");
    }

}
