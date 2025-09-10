package net.pool.station.core.features.category.service;

import lombok.RequiredArgsConstructor;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.category.Category;
import net.pool.station.core.domain.category.CategoryUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryUseCaseService implements CategoryUseCase {
    @Override
    public <T extends Enum<?> & EnumProperty> List<Category> findAll(String search, Class<T> clazz) {
        return Stream.of(clazz.getEnumConstants())
                .map(Category::of)
                .filter(e -> {
                    if (MyObjectUtils.isNotEmpty(search)) {
                        return MyObjectUtils
                                .containIgnoreCase(e.name(), search);
                    }

                    return true;
                })
                .toList();
    }
}
