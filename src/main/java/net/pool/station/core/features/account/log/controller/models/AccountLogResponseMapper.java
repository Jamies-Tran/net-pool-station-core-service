package net.pool.station.core.features.account.log.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.account.log.AccountLog;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface AccountLogResponseMapper extends ModelMapper<AccountLogResponse, AccountLog> {
}
