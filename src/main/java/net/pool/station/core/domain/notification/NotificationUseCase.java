package net.pool.station.core.domain.notification;

import java.util.List;

public interface NotificationUseCase {
    void pushNotification(List<NotifyMessage> notifyMessages);
}
