package net.pool.station.core.domain.category;

import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

import java.util.List;

public interface CategoryUseCase {
    <T extends Enum<?> & EnumProperty> List<Category> findAll(String search, Class<T> clazz);
}
