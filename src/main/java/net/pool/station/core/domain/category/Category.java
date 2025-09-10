package net.pool.station.core.domain.category;

import lombok.Builder;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Builder
public record Category(
        String code,
        String name
) {
    public static <T extends Enum<?> & EnumProperty> Category of(T enumCategory) {
        return Category.builder()
                .code(enumCategory.getCode())
                .name(enumCategory.getName())
                .build();
    }
}
