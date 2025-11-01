package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EGameGenre implements EnumProperty {
    ACTION("ACTION", "Hành động"),
    ADVENTURE("ADVENTURE", "Phiêu lưu"),
    RPG("RPG", "Nhập vai"),
    SIMULATION("SIMULATION", "Mô phỏng"),
    STRATEGY("STRATEGY", "Chiến thuật"),
    SPORTS("SPORTS", "Thể thao"),
    OPEN_WORLD("OPEN_WORLD", "Thế giới mở"),

    CAROM("CAROM", "3 băng"),
    POOL("POOL", "Bi lỗ");

    String code;
    String name;
}
