package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EIntervalType {
    HOUR("HOUR", 3600),
    HALF_HOUR("HALF_HOUR", 1800),;

    String code;
    Integer value;
}
