package net.pool.station.core.features.schedule.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.schedule.Schedule;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface ScheduleRequestMapper extends ModelMapper<ScheduleRequest, Schedule> {
}
