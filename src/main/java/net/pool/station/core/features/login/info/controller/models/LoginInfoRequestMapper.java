package net.pool.station.core.features.login.info.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.login.info.LoginInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LoginInfoRequestMapper extends ModelMapper<LoginInfoRequest, LoginInfo> {
}
