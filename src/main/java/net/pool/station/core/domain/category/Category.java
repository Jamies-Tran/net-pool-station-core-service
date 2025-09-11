package net.pool.station.core.domain.category;

import lombok.Builder;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;
import net.pool.station.core.bootstrap.configuration.enums.EnumTypeProperty;

@Builder
public record Category(
        String code,
        String name,
        String type
) {
    public static <T extends Enum<?> & EnumProperty> Category of(T enumCategory) {
        return Category.builder()
                .code(enumCategory.getCode())
                .name(enumCategory.getName())
                .build();
    }

    public static <T extends Enum<?> & EnumTypeProperty> Category ofType(T enumCategory) {
        return Category.builder()
                .code(enumCategory.getCode())
                .name(enumCategory.getName())
                .type(enumCategory.getType())
                .build();
    }
}
