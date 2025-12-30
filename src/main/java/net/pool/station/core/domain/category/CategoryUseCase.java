package net.pool.station.core.domain.category;

import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;
import net.pool.station.core.bootstrap.configuration.enums.EnumTypeProperty;

import java.util.List;

public interface CategoryUseCase {
    <T extends Enum<?> & EnumProperty> List<Category> findAll(String search, Class<T> clazz);


    <T extends Enum<?> & EnumTypeProperty> List<Category> findAllType(
            String search,
            String type,
            Class<T> clazz
    );

    <T extends Enum<?> & EnumTypeProperty> List<Category> findAllType(String search, Class<T> clazz);
}
