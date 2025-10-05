package net.pool.station.core.features.station.space.timeslot.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface StationSpaceTimeSlotEntityMapper extends EntityMapper<StationSpaceTimeSlotEntity, StationSpaceTimeSlot> {
    void update(@MappingTarget StationSpaceTimeSlotEntity target, StationSpaceTimeSlot source);
}
