package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EAccountStatus implements EnumProperty {
    ENABLE("ENABLE", "Kích hoạt"),
    DISABLE("DISABLE", "Vô hiệu hóa");

    String code;
    String name;
}
