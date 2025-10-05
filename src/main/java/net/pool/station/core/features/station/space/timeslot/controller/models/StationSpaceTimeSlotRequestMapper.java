package net.pool.station.core.features.station.space.timeslot.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlot;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationSpaceTimeSlotRequestMapper extends ModelMapper<StationSpaceTimeSlotRequest,
        StationSpaceTimeSlot> {
}
