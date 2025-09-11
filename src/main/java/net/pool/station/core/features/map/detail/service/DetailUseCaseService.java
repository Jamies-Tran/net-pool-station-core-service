package net.pool.station.core.features.map.detail.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.domain.map.detail.Detail;
import net.pool.station.core.domain.map.detail.DetailUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DetailUseCaseService implements DetailUseCase {
    DetailQueryService queryService;

    @Override
    @Transactional(readOnly = true)
    public Optional<Detail.Result> findDetailByPlaceId(DomainCode<String> placeId) {
        return queryService.findByPlaceId(placeId.value());
    }
}
