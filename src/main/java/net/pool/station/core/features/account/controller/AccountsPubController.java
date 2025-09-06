package net.pool.station.core.features.account.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.features.account.controller.models.AccountRequest;
import net.pool.station.core.features.account.controller.models.AccountRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountsPubController implements AccountsPubApi {
    AccountUseCase useCase;

    AccountRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> saveForPlayer(AccountRequest request) {
        useCase.save(requestMapper.toDto(request), ERole.PLAYER);

        return MyValueResponse.successNoData();
    }
}
