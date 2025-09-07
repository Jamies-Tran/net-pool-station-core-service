package net.pool.station.core.features.login.info.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.domain.login.info.LoginInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LoginInfoDaoMapper extends DaoMapper<LoginInfoDao, LoginInfo> {
}
