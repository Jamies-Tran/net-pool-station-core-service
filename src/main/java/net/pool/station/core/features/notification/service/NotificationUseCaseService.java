package net.pool.station.core.features.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.domain.notification.NotificationUseCase;
import net.pool.station.core.domain.notification.Notification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationUseCaseService implements NotificationUseCase {
    NotificationCommandService commandService;

    @Override
    @Transactional
    public void pushNotification(List<Notification> notifications) {
        notifications.forEach(notification -> {
            try {
                FirebaseMessaging.getInstance().send(notification.firebaseMessage());
            } catch (Exception e) {
                log.error("[NotificationUseCaseService.pushNotification(...)] error: {}", e.getMessage());
            }
        });

        commandService.saveAll(notifications);
    }
}
