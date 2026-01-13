package net.pool.station.core.features.match.joining.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistration;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchJoiningRegistrationResponseMapper extends ModelMapper<MatchJoiningRegistrationResponse,
        MatchJoiningRegistration> {
}
