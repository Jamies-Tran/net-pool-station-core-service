package net.pool.station.core.domain.map.detail;

import net.pool.station.core.domain.DomainKey;

import java.util.Optional;

public interface DetailUseCase {
    Optional<Detail.Result> findDetailByPlaceId(DomainKey<String> placeId);
}
