package net.pool.station.core.features.match.making.match.making.controller.models.schedule;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.match.schedule.MatchSchedule;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchScheduleRequestMapper extends ModelMapper<MatchScheduleRequest, MatchSchedule> {
}
