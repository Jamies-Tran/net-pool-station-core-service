package net.pool.station.core.features.login.info.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.fcm.info.FcmInfo;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface FcmInfoRequestMapper extends ModelMapper<FcmInfoRequest, FcmInfo> {
}
