package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EMatchJoiningRegistrationStatus implements EnumProperty {
    DELIVERED("DELIVERED", "Đã gửi"),
    ACCEPT("ACCEPT", "Chấp nhận"),
    DENY("DENY", "Từ chối");

    String code;
    String name;
}
