package net.pool.station.core.features.station.station.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.Station;
import net.pool.station.core.domain.station.StationCriteria;
import net.pool.station.core.features.station.station.repository.database.StationEntityMapper;
import net.pool.station.core.features.station.station.repository.database.StationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationQueryService {
    StationRepository repository;

    StationEntityMapper mapper;

    protected Optional<Station> findById(Long stationId) {
        return repository.findByStationId(stationId)
                .map(mapper::toDto);
    }

    protected Page<Station> findAll(StationCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected Page<String> findAllStationProvinces(String province, Pageable pageable) {
        return repository.findAllStationProvince(province, pageable);
    }

    protected Page<String> findAllStationCommunes(String commune, Pageable pageable) {
        return repository.findAllStationCommune(commune, pageable);
    }

    protected Page<String> findAllStationDistricts(String district, Pageable pageable) {
        return repository.findAllStationDistrict(district, pageable);
    }
}
