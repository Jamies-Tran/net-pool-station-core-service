package net.pool.station.core.features.category.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.category.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface CategoryResponseMapper extends ModelMapper<CategoryResponse, Category> {
}
