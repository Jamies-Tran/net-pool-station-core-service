package net.pool.station.core.features.notification.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.notification.Notification;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface NotificationMapper extends EntityMapper<NotificationEntity, Notification> {
}
