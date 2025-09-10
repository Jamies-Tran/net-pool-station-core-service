package net.pool.station.core.features.account.self.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.features.account.self.controller.models.AccountRequest;
import net.pool.station.core.features.account.self.controller.models.AccountRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController implements AccountApi {
    AccountUseCase useCase;

    AccountRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long accountId, AccountRequest request) {
        useCase.update(DomainCode.of(accountId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> enable(Long accountId) {
        useCase.enable(DomainCode.of(accountId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long accountId) {
        useCase.disable(DomainCode.of(accountId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long accountId) {
        useCase.delete(DomainCode.of(accountId));

        return MyValueResponse.successNoData();
    }
}
