package net.pool.station.core.features.map.detail.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.map.detail.Detail;
import net.pool.station.core.features.map.detail.repository.feign.DetailFeignMapper;
import net.pool.station.core.features.map.detail.repository.feign.DetailPlaceHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DetailQueryService {
    DetailPlaceHolder placeHolder;

    DetailFeignMapper feignMapper;

    protected Optional<Detail.Result> findByPlaceId(String placeId) {
        try {
            return placeHolder.findDetail(placeId).results()
                    .stream()
                    .findAny()
                    .map(feignMapper::toDto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
