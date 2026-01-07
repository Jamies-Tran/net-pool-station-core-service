package net.pool.station.core.features.match.invitation.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.match.invitation.MatchInvitation;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchInvitationMapper extends EntityMapper<MatchInvitationEntity, MatchInvitation> {
}
