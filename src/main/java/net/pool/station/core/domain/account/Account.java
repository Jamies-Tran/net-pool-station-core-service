package net.pool.station.core.domain.account;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.domain.role.Role;

import java.util.List;

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
        @With Role role,
        @With List<Station> stations
) {
    public record Station(
            String stationId,
            String stationCode,
            String stationName
    ) {}
}
