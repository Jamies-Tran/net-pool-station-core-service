package net.pool.station.core.features.notification.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.notification.Notification;
import net.pool.station.core.features.notification.repository.database.NotificationEntity;
import net.pool.station.core.features.notification.repository.database.NotificationMapper;
import net.pool.station.core.features.notification.repository.database.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationCommandService {
    NotificationRepository repository;

    NotificationMapper mapper;

    protected void saveAll(List<Notification> notification) {
        List<NotificationEntity> entityList = mapper.toEntity(notification);

        repository.saveAll(entityList);
    }
}
