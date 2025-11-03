package net.pool.station.core.features.role.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.role.Role;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface RoleResponseMapper extends ModelMapper<RoleResponse, Role> {
}
