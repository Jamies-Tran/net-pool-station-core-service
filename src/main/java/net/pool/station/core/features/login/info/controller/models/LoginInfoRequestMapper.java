package net.pool.station.core.features.login.info.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.login.info.LoginInfo;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface LoginInfoRequestMapper extends ModelMapper<LoginInfoRequest, LoginInfo> {
}
