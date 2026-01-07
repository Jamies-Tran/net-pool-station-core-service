package net.pool.station.core.features.match.invitation.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.match.invitation.MatchInvitation;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchInvitationRequestMapper extends ModelMapper<MatchInvitationRequest, MatchInvitation> {
}
