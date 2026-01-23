package net.pool.station.core.features.match.schedule.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.match.schedule.MatchSchedule;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchScheduleMapper extends EntityMapper<MatchScheduleEntity, MatchSchedule> {
}
