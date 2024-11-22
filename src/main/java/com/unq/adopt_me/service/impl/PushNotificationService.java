package com.unq.adopt_me.service.impl;
import com.google.gson.Gson;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;

import java.util.HashMap;
import java.util.Map;

public class PushNotificationService {
    private static final String PUBLIC_KEY = "BM3tH-xTaH4xUekOt34I9RkfkwOD2_hj_jtTG1KjtXcBSYtOURtjK2Ao0q48v74tCowwG8Y7kHvv4nSHwogqo1c";
    private static final String PRIVATE_KEY = "GqjECivU7ND7hZCKRLqYXk01tAq9qlTBFvGXzHvAle4";

    public static void main(String[] args) {
        try {
            // Datos de la suscripción del cliente (debes recibirlos del frontend)
            String endpoint = "https://fcm.googleapis.com/fcm/send/...";
            String p256dh = "CLAVE_P256DH_DEL_CLIENTE";
            String auth = "CLAVE_AUTH_DEL_CLIENTE";

            // Crear el payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("title", "¡Hola desde el servidor!");
            payload.put("body", "Este es un mensaje de prueba.");
            payload.put("url", "https://tusitio.com");

            // Convertir el payload a JSON usando Gson
            Gson gson = new Gson();
            String jsonPayload = gson.toJson(payload);

            // Crear la notificación
            Notification notification = new Notification(endpoint, p256dh, auth, jsonPayload);

            // Configurar el servicio Push con las claves VAPID
            PushService pushService = new PushService();
            pushService.setPublicKey(PUBLIC_KEY);
            pushService.setPrivateKey(PRIVATE_KEY);

            // Enviar la notificación
            pushService.send(notification);

            System.out.println("¡Notificación enviada con éxito!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
