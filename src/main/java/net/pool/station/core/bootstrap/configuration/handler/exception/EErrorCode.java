package net.pool.station.core.bootstrap.configuration.handler.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum EErrorCode {
    AUTHORIZE_EXCEPTION("AUTH_001"),
    TOKEN_EXPIRED("AUTH_002"),
    NO_AUTHORITY("AUTH_003"),
    ACCESS_DENIED("AUTH_004"),
    RESOURCE_NOT_FOUND("RESOURCE_001"),
    RESOURCE_DUPLICATED("RESOURCE_002"),
    RESOURCE_NOT_VALID("RESOURCE_003"),
    RESOURCE_VALIDATE_FAIL("RESOURCE_003"),
    SERVER_ERROR("SERVER_001"),
    NULL_ERROR("NULL_001"),
    ARGUMENT_ERROR("ARGUMENT_001"),
    SERVICE_ERROR("SERVICE_001"),
    REQUEST_NOT_VALID("REQUEST_001"),
    LOGIN_EXPIRED("LOGIN_001"),
    LOGIN_NOT_VALID("LOGIN_002"),;

    String code;
}
