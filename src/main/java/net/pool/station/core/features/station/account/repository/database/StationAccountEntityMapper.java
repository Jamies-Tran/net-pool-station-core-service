package net.pool.station.core.features.station.account.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.account.StationAccount;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationAccountEntityMapper extends EntityMapper<StationAccountEntity, StationAccount> {
}
