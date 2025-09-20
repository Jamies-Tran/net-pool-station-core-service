package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ESpaceStatus {
    ACTIVE("ACTIVE", "Đang hoạt động"),
    INACTIVE("INACTIVE", "Tạm ngưng");

    String code;
    String name;
}
