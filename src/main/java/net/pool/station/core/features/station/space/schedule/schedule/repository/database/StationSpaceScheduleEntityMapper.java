package net.pool.station.core.features.station.space.schedule.schedule.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.space.schedule.StationSpaceSchedule;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationSpaceScheduleEntityMapper extends EntityMapper<StationSpaceScheduleEntity,
        StationSpaceSchedule> {
}
