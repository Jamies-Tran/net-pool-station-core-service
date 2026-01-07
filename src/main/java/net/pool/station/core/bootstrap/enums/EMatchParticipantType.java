package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public enum EMatchParticipantType implements EnumProperty {
    HOST("HOST", "Chủ phòng"),
    MEMBER("MEMBER", "Thành viên");

    String code;
    String name;
}
