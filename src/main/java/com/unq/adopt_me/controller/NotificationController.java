package com.unq.adopt_me.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @PostMapping("/save")
    public ResponseEntity<String> saveSubscription(@RequestBody SubscriptionRequest subscription) {
        // Aquí guardas el token en la base de datos
        System.out.println("Token recibido: " + subscription.getToken());
        return ResponseEntity.ok("Token guardado correctamente");
    }

    // Clase para modelar el token
    public static class SubscriptionRequest {
        private String token;

        // Getters y setters
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
