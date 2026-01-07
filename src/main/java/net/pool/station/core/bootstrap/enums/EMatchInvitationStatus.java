package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EMatchInvitationStatus implements EnumProperty {
    SENT("SENT", "Đã gửi"),
    ACCEPTED("ACCEPTED", "Chấp nhận"),
    DENIED("DENIED", "Từ chối");

    String code;
    String name;
}
