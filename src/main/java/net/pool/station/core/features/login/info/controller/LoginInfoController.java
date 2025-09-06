package net.pool.station.core.features.login.info.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.login.info.LoginInfoUseCase;
import net.pool.station.core.features.login.info.controller.models.LoginInfoGeoRequest;
import net.pool.station.core.features.login.info.controller.models.LoginInfoResponse;
import net.pool.station.core.features.login.info.controller.models.LoginInfoResponseMapper;
import net.pool.station.core.features.login.info.controller.models.RefreshTokenRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginInfoController implements LoginInfoApi {
    LoginInfoUseCase useCase;

    LoginInfoResponseMapper responseMapper;

    @Override
    public void updateGeo(LoginInfoGeoRequest request) {
        useCase.update(request.latitude(), request.longitude());
    }

    @Override
    public void logout() {
        useCase.delete();
    }

    @Override
    public MyValueResponse<LoginInfoResponse> refresh(RefreshTokenRequest request) {
        LoginInfo refresh = useCase.update(request.refreshToken());

        return MyValueResponse.success(responseMapper.toModel(refresh));
    }
}
