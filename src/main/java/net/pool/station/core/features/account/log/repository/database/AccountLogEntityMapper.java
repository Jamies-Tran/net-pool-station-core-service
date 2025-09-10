package net.pool.station.core.features.account.log.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.account.log.AccountLog;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface AccountLogEntityMapper extends EntityMapper<AccountLogEntity, AccountLog> {

}
