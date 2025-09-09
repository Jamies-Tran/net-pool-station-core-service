package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EMailType {
    VALIDATE_ACCOUNT("VALIDATE_ACCOUNT"),
    CHANGE_PASSWORD("CHANGE_PASSWORD"),
    CHANGE_EMAIL("CHANGE_EMAIL");

    String code;
}
