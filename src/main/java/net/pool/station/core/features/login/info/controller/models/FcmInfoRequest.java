package net.pool.station.core.features.login.info.controller.models;

public record FcmInfoRequest(
        String deviceId,
        String deviceType,
        String fcmToken
) {
}
