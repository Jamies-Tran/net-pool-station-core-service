package net.pool.station.core.features.schedule.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.features.timeslot.controller.models.TimeSlotResponseMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class, uses = {TimeSlotResponseMapper.class})
public interface ScheduleResponseMapper extends ModelMapper<ScheduleResponse, Schedule> {
}
