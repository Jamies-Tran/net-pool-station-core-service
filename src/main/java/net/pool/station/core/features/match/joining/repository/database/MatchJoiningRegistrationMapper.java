package net.pool.station.core.features.match.joining.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Repository;

@Mapper(config = MapStructConfig.class)
public interface MatchJoiningRegistrationMapper extends EntityMapper<MatchJoiningRegistrationEntity,
        MatchJoiningRegistration> {
    void update(@MappingTarget MatchJoiningRegistrationEntity target, MatchJoiningRegistration source);
}
