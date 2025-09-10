package net.pool.station.core.features.login.info.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.login.info.LoginInfo;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface LoginInfoDaoMapper extends DaoMapper<LoginInfoDao, LoginInfo> {
}
