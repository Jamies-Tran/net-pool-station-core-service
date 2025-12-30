package net.pool.station.core.features.match.participant.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchParticipantMapper extends EntityMapper<MatchParticipantEntity, MatchParticipant> {
}
