package net.pool.station.core.features.account.self.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapStructConfig.class)
public interface AccountResponseMapper extends ModelMapper<AccountResponse, Account> {
}
