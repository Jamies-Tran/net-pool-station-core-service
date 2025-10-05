package net.pool.station.core.features.station.account.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.domain.station.account.StationAccount;
import net.pool.station.core.domain.station.account.StationAccountId;
import net.pool.station.core.features.station.account.repository.database.StationAccountEntityMapper;
import net.pool.station.core.features.station.account.repository.database.StationAccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationAccountCommandService {
    StationAccountRepository repository;

    StationAccountEntityMapper mapper;

    protected void save(StationAccount stationAccount) {
        repository.save(mapper.toEntity(stationAccount));
    }

    protected void updateStatus(StationAccountId stationAccountId, EAccountStatus status) {
        repository.findByStationAccountId(stationAccountId)
                .ifPresentOrElse(
                        foundStationAccount -> {
                            foundStationAccount.setStatusCode(status.getCode());
                            foundStationAccount.setStatusName(status.getName());
                            repository.save(foundStationAccount);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }
}
