package net.pool.station.core.features.account.account.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.features.account.account.controller.models.AccountRequest;
import net.pool.station.core.features.account.account.controller.models.AccountRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController implements AccountApi {
    AccountUseCase useCase;

    AccountRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long accountId, AccountRequest request) {
        useCase.update(DomainKey.of(accountId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> enable(Long accountId) {
        useCase.enable(DomainKey.of(accountId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long accountId) {
        useCase.disable(DomainKey.of(accountId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long accountId) {
        useCase.delete(DomainKey.of(accountId));

        return MyValueResponse.successNoData();
    }
}
