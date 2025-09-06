package net.pool.station.core.domain.account;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.domain.role.Role;

@Builder
public record Account(
        Long accountId,
        @With Long roleId,
        String avatar,
        String username,
        String password,
        String identification,
        String phone,
        String email,
        Boolean deleted,
        String statusCode,
        String statusName,
        @With Role role
) {
}
