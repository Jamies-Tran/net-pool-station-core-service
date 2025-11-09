package net.pool.station.core.features.wallet.ledger.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.wallet.ledger.WalletLedgerCriteria;
import net.pool.station.core.domain.wallet.ledger.WalletLedgerUseCase;
import net.pool.station.core.features.wallet.ledger.controller.models.WalletLedgerResponse;
import net.pool.station.core.features.wallet.ledger.controller.models.WalletLedgerResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletLedgersController implements WalletLedgersApi {
    WalletLedgerUseCase walletLedgerUseCase;

    WalletLedgerResponseMapper responseMapper;

    @Override
    public MyPageResponse<WalletLedgerResponse> findAll(
            List<LocalDateTime> timeRange,
            String sorter, Integer current, Integer pageSize
    ) {
        WalletLedgerCriteria criteria = WalletLedgerCriteria.of(timeRange);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<WalletLedgerResponse> responses = walletLedgerUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
