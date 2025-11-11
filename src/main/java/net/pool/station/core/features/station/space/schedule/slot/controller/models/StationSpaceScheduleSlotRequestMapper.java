package net.pool.station.core.features.station.space.schedule.slot.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.space.schedule.slot.StationSpaceScheduleSlot;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationSpaceScheduleSlotRequestMapper extends ModelMapper<StationSpaceScheduleSlotListRequest.StationSpaceScheduleSlotRequest,
        StationSpaceScheduleSlot> {
}
