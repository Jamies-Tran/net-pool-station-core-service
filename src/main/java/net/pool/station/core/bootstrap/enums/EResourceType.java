package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EResourceType {
    PC("PC", "PC (Máy bàn)"),
    BILLIARD_TABLE("BILLIARD_TABLE", "Bàn bida"),
    CONSOLE("GAMING_CONSOLE", "Máy chơi game console");

    String code;
    String name;
}
