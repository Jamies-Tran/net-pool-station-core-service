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
    BOOKING_DIRECT_PAYMENT("BOOKING_DIRECT_PAYMENT", "Thanh toán trực tiếp booking"),
    MATCH_MAKING_DEPOSIT("MATCH_MAKING_DEPOSIT", "Thanh toán đặt cọc ghép trận"),
    MATCH_MAKING_PAYMENT("MATCH_MAKING_PAYMENT", "Thanh toán tiền ghép trận"),
    MATCH_PARTICIPANT_PAYMENT("MATCH_PARTICIPANT_PAYMENT", "Thanh toán phí tham gia room"),
    MATCH_PARTICIPANT_DIRECT_PAYMENT("MATCH_PARTICIPANT_DIRECT_PAYMENT", "Thanh toán trực tiếp phí tham gia room"),
    MATCH_PARTICIPANT_REFUND("MATCH_PARTICIPANT_REFUND", "Trả tiền dư khi tham gia room"),
    MATCH_MAKING_DEPOSIT_REFUND("MATCH_MAKING_DEPOSIT_REFUND", "Hoàn tiền đặt cọc ghép trận"),
    MATCH_PARTICIPANT_DEPOSIT_REFUND("MATCH_PARTICIPANT_DEPOSIT_REFUND", "Hoàn tiền đặt cọc phí tham gia trận"),
    WALLET_PAYMENT("WALLET_PAYMENT", "Thanh toán wallet"),;

    String code;
    String name;
}
