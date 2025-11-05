package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EPaymentMethod implements EnumProperty {
    DIRECT("DIRECT", "Thanh toán trực tiếp"),
    BANK_TRANSFER("BANK_TRANSFER", "Chuyển khoản ngân hàng");

    String code;
    String name;
}
