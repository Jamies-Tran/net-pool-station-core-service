package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ENotificationType implements EnumProperty {
    BOOKING("BOOKING", "booking"),
    MATCH_MAKING("MATCH_MAKING", "Xếp trận"),
    MATCH_MAKING_INVITATION("MATCH_MAKING_INVITATION", "Lời mời xếp trận");

    String code;
    String name;
}
