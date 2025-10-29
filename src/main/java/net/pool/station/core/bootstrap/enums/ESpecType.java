package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ESpecType implements EnumProperty {
    PC("PC", "Máy PC"),
    Billiard_TABLE("BT", "Bàn BiDa"),
    CONSOLE("CS", "Máy Console"),;

    String code;
    String name;
}
