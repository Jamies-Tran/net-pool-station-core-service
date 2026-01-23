package net.pool.station.core.features.match.schedule.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.match.schedule.MatchSchedule;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchScheduleDaoMapper extends DaoMapper<MatchScheduleDao, MatchSchedule> {
}
