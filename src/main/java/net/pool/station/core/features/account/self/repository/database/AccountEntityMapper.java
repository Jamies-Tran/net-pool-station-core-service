package net.pool.station.core.features.account.self.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface AccountEntityMapper extends EntityMapper<AccountEntity, Account> {
    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    void update(@MappingTarget AccountEntity target, Account source);
}
