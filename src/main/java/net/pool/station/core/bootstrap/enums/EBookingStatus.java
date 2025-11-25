package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EBookingStatus implements EnumProperty {
    PENDING("PENDING", "Chờ thanh toán"),
    NEW("NEW", "Mới"),
    PROCESSING("PROCESSING", "Đang xử lý"),
    COMPLETED("COMPLETED", "Đã hoàn thành"),
    CANCELED("CANCELED", "Đã hủy");

    String code;
    String name;
}
