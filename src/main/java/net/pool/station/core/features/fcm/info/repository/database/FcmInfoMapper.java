package net.pool.station.core.features.fcm.info.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.fcm.info.FcmInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface FcmInfoMapper extends EntityMapper<FcmInfoEntity, FcmInfo> {
    void update(@MappingTarget FcmInfoEntity entity, FcmInfo dto);
}
