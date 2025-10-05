package net.pool.station.core.domain.station.account;

import net.pool.station.core.domain.DomainKey;

public interface StationAccountUseCase {
    void save(StationAccount stationAccount);

    void enable(DomainKey<StationAccountId> stationAccountId);

    void disable(DomainKey<StationAccountId> stationAccountId);
}
