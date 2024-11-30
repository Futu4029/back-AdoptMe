package com.unq.adopt_me.service.impl;
import com.google.gson.Gson;
import com.unq.adopt_me.common.AbstractServiceResponse;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dao.NotificationDao;
import com.unq.adopt_me.dto.notification.SubscriptionRequest;
import com.unq.adopt_me.entity.notification.NotificationCredentials;
import com.unq.adopt_me.security.CustomUserDetails;
import com.unq.adopt_me.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl extends AbstractServiceResponse implements NotificationService {

    @Value("${push.vapid.public-key}")
    private String PUBLIC_KEY;
    @Value("${push.vapid.private-key}")
    private String PRIVATE_KEY;
    private final String SUCCESS_SAVE_NOTIFICATION = "Credentials saved correctly";
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);


    @Autowired
    private NotificationDao notificationDao;

    @Override
    public void sendNotification(String message, Long userId) {
        try {
            // Datos de la suscripción del cliente (debes recibirlos del frontend)
            NotificationCredentials notificationCredentialsData = notificationDao.findByUserId(userId);
            sendNotificationTo(message, notificationCredentialsData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotificationTo(String message, NotificationCredentials notificationCredentialsData) throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException {
        String endpoint = notificationCredentialsData.getEndpoint();
        String p256dh = notificationCredentialsData.getKeys().getP256dh();
        String auth = notificationCredentialsData.getKeys().getAuth();

        // Crear el payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "¡Adopt Me APP!");
        payload.put("body", message);

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
    }

    @Override
    public GeneralResponse saveToken(SubscriptionRequest token) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        notificationDao.upsert(token.getToken().getEndpoint(), token.getToken().getExpirationTime(), customUserDetails.getUserId(), token.getToken().getKeys().getP256dh(), token.getToken().getKeys().getAuth());
        logger.info("SAVING NOTIFICATION - Saving notification for user [userId: {}]", customUserDetails.getUserId());

        return  generateResponse(SUCCESS_SAVE_NOTIFICATION, null);
    }

}
