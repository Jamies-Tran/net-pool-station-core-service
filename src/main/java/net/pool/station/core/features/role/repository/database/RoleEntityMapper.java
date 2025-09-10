package net.pool.station.core.features.role.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.role.Role;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface RoleEntityMapper extends EntityMapper<RoleEntity, Role> {
}
