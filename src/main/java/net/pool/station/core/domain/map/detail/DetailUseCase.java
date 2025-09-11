package net.pool.station.core.domain.map.detail;

import net.pool.station.core.domain.DomainCode;

import java.util.List;
import java.util.Optional;

public interface DetailUseCase {
    Optional<Detail.Result> findDetailByPlaceId(DomainCode<String> placeId);
}
