package net.pool.station.core.domain.fcm.info;

import lombok.Builder;
import lombok.With;

@Builder
public record FcmInfo(
        Long fcmInfoId,
        @With Long accountId,
        String deviceId,
        String deviceType,
        String fcmToken
) {
}
