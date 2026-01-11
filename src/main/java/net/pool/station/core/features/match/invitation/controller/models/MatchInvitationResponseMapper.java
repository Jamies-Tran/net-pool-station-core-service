package net.pool.station.core.features.match.invitation.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.match.invitation.MatchInvitation;
import net.pool.station.core.features.account.account.controller.models.AccountResponseMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class, uses = {AccountResponseMapper.class})
public interface MatchInvitationResponseMapper extends ModelMapper<MatchInvitationResponse, MatchInvitation> {
}
