package net.pool.station.core.features.account.account.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.account.Account;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface AccountResponseMapper extends ModelMapper<AccountResponse, Account> {
}
