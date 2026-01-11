package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EMatchParticipantReadyStatus implements EnumProperty {
    READY("READY", "Đã sẵn sàng"),
    NOT_READY("NOT_READY", "Chưa sẵn sàng");

    String code;
    String name;
}
