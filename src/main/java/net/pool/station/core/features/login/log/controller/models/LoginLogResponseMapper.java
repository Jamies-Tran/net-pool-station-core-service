package net.pool.station.core.features.login.log.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.login.log.LoginLog;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface LoginLogResponseMapper extends ModelMapper<LoginLogResponse, LoginLog> {
}
