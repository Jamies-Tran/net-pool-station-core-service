package net.pool.station.core.features.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.domain.notification.NotificationUseCase;
import net.pool.station.core.domain.notification.NotifyMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationUseCaseService implements NotificationUseCase {
    @Override
    public void pushNotification(List<NotifyMessage> notifyMessages) {
        notifyMessages.forEach(notifyMessage -> {
            try {
                FirebaseMessaging.getInstance().send(notifyMessage.firebaseMessage());
            } catch (Exception e) {
                log.error("[NotificationUseCaseService.pushNotification(...)] error: {}", e.getMessage());
            }
        });
    }
}
