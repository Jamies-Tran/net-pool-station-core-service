package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EMatchMakingStatus implements EnumProperty {
    DRAFT("DRAFT", "Nháp"),
    PENDING("PENDING", "Đang chờ"),
    CANCEL("CANCEL", "Đã hủy"),
    STARTED("STARTED", "Bắt đầu"),
    FINISHED("FINISHED", "Kết thúc"),;


    String code;
    String name;
}
