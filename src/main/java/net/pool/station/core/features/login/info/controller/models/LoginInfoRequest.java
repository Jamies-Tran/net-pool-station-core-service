package net.pool.station.core.features.login.info.controller.models;

public record LoginInfoRequest(
        String email,
        String password,
        Double latitude,
        Double longitude
) {
}
