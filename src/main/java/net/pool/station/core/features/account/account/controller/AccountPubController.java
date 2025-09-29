package net.pool.station.core.features.account.account.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.features.account.account.controller.models.AccountResponse;
import net.pool.station.core.features.account.account.controller.models.AccountResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountPubController implements AccountPubApi {
    AccountUseCase useCase;

    AccountResponseMapper responseMapper;

    @Override
    public MyValueResponse<AccountResponse> findById(Long accountId) {
        AccountResponse response = useCase.findById(DomainKey.of(accountId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }
}
