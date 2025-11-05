package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EPaymentStatus implements EnumProperty {
    PENDING("PENDING", "đang chờ xử lý"),
    CANCELLED("CANCELLED", "Hủy"),
    UNDERPAID("UNDERPAID", "Chưa thanh toán đủ"),
    PAID("PAID", "Đã thanh toán"),
    EXPIRED("EXPIRED", "Hết hạn thanh toán"),
    PROCESSING("PROCESSING", "Đang xử lý"),
    FAILED("FAILED", "Xử lý không thành công"),;

    String code;
    String name;
}
