package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;
import net.pool.station.core.bootstrap.configuration.enums.EnumTypeProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ELogType implements EnumTypeProperty {
    ACCOUNT_SAVE("SAVE", "Tạo mới", "ACCOUNT"),
    ACCOUNT_VERIFY("VERIFY", "Xác nhận email", "ACCOUNT"),
    ACCOUNT_ENABLE("ENABLE", "Kích hoạt", "ACCOUNT"),
    ACCOUNT_DISABLE("DISABLE", "Vô hiệu hóa", "ACCOUNT"),
    ACCOUNT_UPDATE("UPDATE", "Cập nhật", "ACCOUNT"),

    LOGIN_LOGIN("LOGIN", "Đăng nhập", "LOGIN"),
    LOGIN_LOGOUT("LOGOUT", "Đăng xuất", "LOGIN"),

    STATION_SAVE("SAVE", "Tạo mới", "STATION"),
    STATION_UPDATE("UPDATE", "Cập nhật", "STATION"),
    STATION_ACCEPT("ACCEPT", "Duyệt", "STATION"),
    STATION_REJECT("REJECT", "Từ chối", "STATION"),
    STATION_ACTIVE("ACTIVE", "Kích hoạt", "STATION"),
    STATION_INACTIVE("INACTIVE", "Vô hiệu hóa", "STATION"),;


   String code;
   String name;
   String type;
}
