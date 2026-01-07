package net.pool.station.core.features.match.making.match.making.controller.models.participant;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchParticipantResponseMapper extends ModelMapper<MatchParticipantResponse, MatchParticipant> {
}
