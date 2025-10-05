package net.pool.station.core.features.timeslot.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.timeslot.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface TimeSlotEntityMapper extends EntityMapper<TimeSlotEntity, TimeSlot> {
    void update(@MappingTarget TimeSlotEntity target, TimeSlot source);
}
