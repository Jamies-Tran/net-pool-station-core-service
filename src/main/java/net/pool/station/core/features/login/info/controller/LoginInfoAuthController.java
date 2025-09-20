package net.pool.station.core.features.login.info.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.login.info.LoginInfoUseCase;
import net.pool.station.core.features.login.info.controller.models.LoginInfoRequest;
import net.pool.station.core.features.login.info.controller.models.LoginInfoRequestMapper;
import net.pool.station.core.features.login.info.controller.models.LoginInfoResponse;
import net.pool.station.core.features.login.info.controller.models.LoginInfoResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginInfoAuthController implements LoginInfoAuthApi {
    LoginInfoUseCase useCase;

    LoginInfoRequestMapper requestMapper;

    LoginInfoResponseMapper responseMapper;

    @Override
    public MyValueResponse<LoginInfoResponse> login(LoginInfoRequest request) {
        LoginInfo login = useCase.saveOrUpdate(request.email(), request.password());

        return MyValueResponse.success(responseMapper.toModel(login));
    }
}
