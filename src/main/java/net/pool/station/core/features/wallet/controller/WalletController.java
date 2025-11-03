package net.pool.station.core.features.wallet.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.wallet.WalletUseCase;
import net.pool.station.core.features.wallet.controller.models.WalletResponse;
import net.pool.station.core.features.wallet.controller.models.WalletResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController implements WalletApi {
    WalletUseCase walletUseCase;

    WalletResponseMapper responseMapper;

    @Override
    public MyValueResponse<WalletResponse> findByAccountId(Long accountId) {
        WalletResponse response = walletUseCase.findByAccountId(DomainKey.of(accountId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }

    @Override
    public MyValueResponse<?> enable(Long accountId) {
        walletUseCase.enable(DomainKey.of(accountId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long accountId) {
        walletUseCase.disable(DomainKey.of(accountId));

        return MyValueResponse.successNoData();
    }
}
