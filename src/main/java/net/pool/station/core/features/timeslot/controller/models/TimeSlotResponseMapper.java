package net.pool.station.core.features.timeslot.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.timeslot.TimeSlot;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface TimeSlotResponseMapper extends ModelMapper<TimeSlotResponse, TimeSlot> {
}
