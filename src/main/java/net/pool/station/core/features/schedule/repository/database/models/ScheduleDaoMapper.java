package net.pool.station.core.features.schedule.repository.database.models;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.schedule.Schedule;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface ScheduleDaoMapper extends DaoMapper<ScheduleDao, Schedule> {
}
