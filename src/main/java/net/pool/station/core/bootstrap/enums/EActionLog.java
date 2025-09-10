package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EActionLog implements EnumProperty {
    ACCOUNT_SAVE("SAVE", "Tạo mới", "ACCOUNT"),
    ACCOUNT_VERIFY("VERIFY", "Xác nhận email", "ACCOUNT"),
    ACCOUNT_ENABLE("ENABLE", "Kích hoạt", "ACCOUNT"),
    ACCOUNT_DISABLE("DISABLE", "Vô hiệu hóa", "ACCOUNT"),
    ACCOUNT_UPDATE("UPDATE", "Cập nhật", "ACCOUNT");


   String code;
   String name;
   String type;
}
