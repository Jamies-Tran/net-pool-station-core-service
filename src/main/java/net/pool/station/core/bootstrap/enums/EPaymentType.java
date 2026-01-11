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
    MATCH_MAKING_DEPOSIT("MATCH_MAKING_DEPOSIT", "Đặt cọc ghép trận"),
    MATCH_MAKING_DEPOSIT_REFUND("MATCH_MAKING_DEPOSIT_REFUND", "Hoàn tiền đặt cọc ghép trận"),
    WALLET_PAYMENT("WALLET_PAYMENT", "Thanh toán wallet"),;

    String code;
    String name;
}
