package net.pool.station.core.bootstrap.utils;

import lombok.extern.java.Log;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.login.info.LoginInfoUseCase;
import org.springframework.security.core.context.SecurityContextHolder;

public class MyRequestContext {
    public static LoginInfo currentLoginInfo() {
        LoginInfoUseCase loginInfoUseCase = MySpringContext.getBean(LoginInfoUseCase.class);

        return loginInfoUseCase.findCurrentLoginInfo()
                .orElse(LoginInfo.currentLoginInfoEmpty());
    }
}
