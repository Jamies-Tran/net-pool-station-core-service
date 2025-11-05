package net.pool.station.core.bootstrap.rest.response;

public record PayOsResponse<T>(
        String code,
        String desc,
        T data,
        String signature

) {
}
