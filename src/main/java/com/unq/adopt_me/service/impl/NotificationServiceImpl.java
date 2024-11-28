package com.unq.adopt_me.service.impl;
import com.google.gson.Gson;
import com.unq.adopt_me.common.AbstractServiceResponse;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dao.NotificationDao;
import com.unq.adopt_me.dto.notification.SubscriptionRequest;
import com.unq.adopt_me.entity.notification.NotificationCredentials;
import com.unq.adopt_me.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl extends AbstractServiceResponse implements NotificationService {
    private static final String PUBLIC_KEY = "BM3tH-xTaH4xUekOt34I9RkfkwOD2_hj_jtTG1KjtXcBSYtOURtjK2Ao0q48v74tCowwG8Y7kHvv4nSHwogqo1c";
    private static final String PRIVATE_KEY = "GqjECivU7ND7hZCKRLqYXk01tAq9qlTBFvGXzHvAle4";
    private final String SUCCESS_SAVE_NOTIFICATION = "Credentials saved correctly";
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);


    @Autowired
    private NotificationDao notificationDao;

    public void sendNotification(String message) {
        try {
            // Datos de la suscripción del cliente (debes recibirlos del frontend)
            NotificationCredentials notificationCredentialsData = notificationDao.findById(1L).orElseGet(null);
            String endpoint = notificationCredentialsData.getEndpoint();
            String p256dh = notificationCredentialsData.getKeys().getP256dh();
            String auth = notificationCredentialsData.getKeys().getAuth();

            // Crear el payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("title", "¡Hola desde el servidor!");
            payload.put("body", message);
            payload.put("url", "http://127.0.0.1:4200/");

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

            logger.info("SENDING NOTIFICATION - Sending notification with message [message: {}]", message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public GeneralResponse saveToken(SubscriptionRequest token) {
        notificationDao.save(new NotificationCredentials(token.getToken().getEndpoint(), token.getToken().getExpirationTime(), token.getToken().getKeys()));
        logger.info("SAVING NOTIFICATION");

        return  generateResponse(SUCCESS_SAVE_NOTIFICATION, null);
    }
}
