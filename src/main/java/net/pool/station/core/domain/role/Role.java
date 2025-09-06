package net.pool.station.core.domain.role;

import lombok.Builder;

@Builder
public record Role(
        Long roleId,
        String roleCode,
        String roleName
) {
    public static Role empty() {
        return Role.builder()
                .roleCode("")
                .roleName("")
                .build();
    }
}
