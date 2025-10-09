package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EMenuStatus implements EnumProperty {
    ENABLE("ENABLE", "Hoạt động"),
    DISABLE("DISABLE", "Khóa"),;

    String code;
    String name;
}
