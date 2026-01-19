package net.pool.station.core.features.timeslot.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.timeslot.TimeSlot;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface TimeSlotDaoMapper extends DaoMapper<TimeSlotDao, TimeSlot> {
}
