package net.pool.station.core.bootstrap.utils;

import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.domain.login.info.LoginInfo;

import java.util.ArrayList;
import java.util.List;

public class MyAuthorizationUtils {
    public static List<String> authorizeList() {
        LoginInfo currentLoginInfo = MyRequestContext.currentLoginInfo()
                .orElse(LoginInfo.currentLoginInfoEmpty());
        List<String> allowList = new ArrayList<>();
        ERole.findByCode(currentLoginInfo.roleCode())
                .ifPresent(role -> {
                    switch (role) {
                        case SYSTEM_ADMIN -> allowList.addAll(List.of(
                                ERole.PLATFORM_ADMIN.getCode(),
                                ERole.STATION_ADMIN.getCode(),
                                ERole.STATION_OWNER.getCode(),
                                ERole.PLAYER.getCode()
                        ));
                        case PLATFORM_ADMIN -> allowList.addAll(List.of(
                                ERole.STATION_ADMIN.getCode(),
                                ERole.STATION_OWNER.getCode(),
                                ERole.PLAYER.getCode()
                        ));
                        case STATION_OWNER -> allowList.addAll(List.of(
                                ERole.STATION_ADMIN.getCode(),
                                ERole.PLAYER.getCode()
                        ));
                        case STATION_ADMIN -> allowList.add(
                                ERole.PLAYER.getCode()
                        );
                        case PLAYER -> allowList.addAll(List.of());
                        default -> {}
                    }
                });
        return allowList;
    }
}
