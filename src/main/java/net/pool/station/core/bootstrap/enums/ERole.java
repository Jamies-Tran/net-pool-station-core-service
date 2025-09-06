package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ERole implements EnumProperty {
    SYSTEM_ADMIN("SYSTEM_ADMIN", "Quản trị viên hệ thống"),
    PLATFORM_ADMIN("PLATFORM_ADMIN", "Quản trị viên nền tảng"),
    STATION_ADMIN("STATION_ADMIN", "Quản trị viên station"),
    STATION_OWNER("STATION_OWNER", "Chủ sở hữu station"),
    PLAYER("PLAYER", "Khách hàng");

    String code;
    String name;
}
