package net.pool.station.core.features.station.account.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.account.StationAccountId;
import net.pool.station.core.domain.station.account.StationAccountUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationAccountController implements StationAccountApi {
    StationAccountUseCase stationAccountUseCase;

    @Override
    public MyValueResponse<?> enable(Long stationId, Long accountId) {
        StationAccountId id = StationAccountId.of(stationId, accountId);
        stationAccountUseCase.enable(DomainKey.of(id));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long stationId, Long accountId) {
        StationAccountId id = StationAccountId.of(stationId, accountId);
        stationAccountUseCase.disable(DomainKey.of(id));

        return MyValueResponse.successNoData();
    }
}
