package net.pool.station.core.features.station.account.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.account.StationAccount;
import net.pool.station.core.domain.station.account.StationAccountId;
import net.pool.station.core.domain.station.account.StationAccountUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationAccountUseCaseService implements StationAccountUseCase {
    StationAccountCommandService commandService;

    @Override
    @Transactional
    public void save(StationAccount stationAccount) {
        commandService.save(stationAccount);
    }

    @Override
    @Transactional
    public void enable(DomainKey<StationAccountId> stationAccountId) {
        commandService.updateStatus(stationAccountId.value(), EAccountStatus.ENABLE);
    }

    @Override
    @Transactional
    public void disable(DomainKey<StationAccountId> stationAccountId) {
        commandService.updateStatus(stationAccountId.value(), EAccountStatus.DISABLE);
    }
}
