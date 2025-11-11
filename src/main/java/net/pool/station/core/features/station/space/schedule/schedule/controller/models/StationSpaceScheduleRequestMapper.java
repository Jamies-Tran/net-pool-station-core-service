package net.pool.station.core.features.station.space.schedule.schedule.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.space.schedule.StationSpaceSchedule;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationSpaceScheduleRequestMapper extends ModelMapper<StationSpaceScheduleListRequest.StationSpaceScheduleRequest,
        StationSpaceSchedule> {
}
