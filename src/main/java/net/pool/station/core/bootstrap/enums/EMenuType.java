package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EMenuType {
    DRINK("DRINK", "Đồ uống"),
    FOOD("FOOD", "Đồ ăn");

    String code;
    String name;
}
