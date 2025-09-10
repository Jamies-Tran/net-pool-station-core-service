package net.pool.station.core.features.account.log.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.account.log.AccountLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountLogResponseMapper extends ModelMapper<AccountLogResponse, AccountLog> {
}
