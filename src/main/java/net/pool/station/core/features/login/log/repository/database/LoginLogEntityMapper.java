package net.pool.station.core.features.login.log.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.login.log.LoginLog;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface LoginLogEntityMapper extends EntityMapper<LoginLogEntity, LoginLog> {
}
