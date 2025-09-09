package net.pool.station.core.domain.login.info;

import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDateTime;

@Builder
public record LoginInfo(
        Long loginInfoId,
        Long accountId,
        String email,
        String username,
        String roleCode,
        String accessToken,
        LocalDateTime accessExpiredAt,
        String refreshToken,
        LocalDateTime refreshExpiredAt,
        Double latitude,
        Double longitude
) {
    public static LoginInfo currentLoginInfoEmpty() {
        return LoginInfo.builder()
                .accountId(null)
                .email("")
                .username("")
                .roleCode("")
                .build();
    }

    public String hash() {
        return "%s-%s-%s".formatted(email, username, roleCode);
    }

    public Boolean isLoginEmpty() {
        return MyObjectUtils.isEquals(this.hash(), currentLoginInfoEmpty().hash());
    }
}
