package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EPaymentType implements EnumProperty {
    BOOKING_PAYMENT("BOOKING_PAYMENT", "Thanh toán booking"),
    WALLET_PAYMENT("WALLET_PAYMENT", "Thanh toán wallet"),;

    String code;
    String name;
}
