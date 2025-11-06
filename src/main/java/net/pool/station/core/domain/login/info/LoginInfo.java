package net.pool.station.core.domain.login.info;

import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.account.Account;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record LoginInfo(
        Long loginInfoId,
        Long accountId,
        String email,
        List<Account.Station> stations,
        String username,
        String roleCode,
        String accessToken,
        LocalDateTime accessExpiredAt,
        String refreshToken,
        LocalDateTime refreshExpiredAt
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
