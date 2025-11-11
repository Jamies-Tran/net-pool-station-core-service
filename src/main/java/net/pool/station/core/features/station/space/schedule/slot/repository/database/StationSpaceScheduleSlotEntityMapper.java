package net.pool.station.core.features.station.space.schedule.slot.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.space.schedule.slot.StationSpaceScheduleSlot;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationSpaceScheduleSlotEntityMapper extends EntityMapper<StationSpaceScheduleSlotEntity,
        StationSpaceScheduleSlot> {
}
