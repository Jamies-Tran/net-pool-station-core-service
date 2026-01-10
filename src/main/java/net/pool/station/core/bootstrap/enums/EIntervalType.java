package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EIntervalType implements EnumProperty {
    HOUR("HOUR", "1 tiếng",3600),
    HALF_HOUR("HALF_HOUR", "30 phút",1800),;

    String code;
    String name;
    Integer value;
}
