package net.pool.station.core.features.account.self.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.features.account.self.controller.models.AccountRequest;
import net.pool.station.core.features.account.self.controller.models.AccountRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountsController implements AccountsApi {
    AccountUseCase useCase;

    AccountRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> saveForPlatformAdmin(AccountRequest request) {
        useCase.save(requestMapper.toDto(request), ERole.PLATFORM_ADMIN);

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> saveForStationAdmin(AccountRequest request) {
        useCase.save(requestMapper.toDto(request), ERole.STATION_ADMIN);

        return MyValueResponse.successNoData();
    }
}
