package net.pool.station.core.features.schedule.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.schedule.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface ScheduleEntityMapper extends EntityMapper<ScheduleEntity, Schedule> {
    void update(@MappingTarget ScheduleEntity entity, Schedule dto);
}
