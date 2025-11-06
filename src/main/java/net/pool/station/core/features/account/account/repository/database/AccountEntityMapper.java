package net.pool.station.core.features.account.account.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.features.account.account.repository.database.models.StationDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface AccountEntityMapper extends EntityMapper<AccountEntity, Account> {
    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    void update(@MappingTarget AccountEntity target, Account source);

    Account.Station toDto(StationDao dao);

    List<Account.Station> toDtos(List<StationDao> dao);
}
