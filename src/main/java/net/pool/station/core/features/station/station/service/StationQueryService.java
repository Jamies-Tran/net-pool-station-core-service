package net.pool.station.core.features.station.station.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.Station;
import net.pool.station.core.domain.station.StationCriteria;
import net.pool.station.core.features.station.station.repository.database.StationEntity;
import net.pool.station.core.features.station.station.repository.database.StationEntityMapper;
import net.pool.station.core.features.station.station.repository.database.StationRepository;
import net.pool.station.core.features.station.station.repository.database.dao.StationDao;
import net.pool.station.core.features.station.station.repository.database.dao.StationDaoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationQueryService {
    StationRepository repository;

    StationEntityMapper mapper;

    StationDaoMapper daoMapper;

    protected Optional<Station> findById(Long stationId) {
        return repository.findByStationId(stationId)
                .map(mapper::toDto);
    }

    protected Page<Station> findAll(StationCriteria criteria, PageRequest pageRequest) {
        Page<StationDao> stationIdDistance = repository.findAll(criteria, pageRequest.withSort(Sort.unsorted()));
        List<Long> stationIds = stationIdDistance.stream().map(StationDao::getStationId).toList();
        List<Station> station = repository
                .findAllByStationIdIn(stationIds, criteria.latitude(), criteria.longitude(), pageRequest.getSort()).stream()
                .map(s -> mapper.toDto(s.getStation())
                        .withDistance(s.getDistance() / 1000))
                .toList();
        return new PageImpl<>(station, pageRequest, stationIdDistance.getTotalElements());
    }

    protected Page<String> findAllStationProvinces(String province, PageRequest pageRequest) {
        return repository.findAllStationProvince(province, pageRequest);
    }

    protected Page<String> findAllStationCommunes(String commune, PageRequest pageRequest) {
        return repository.findAllStationCommune(commune, pageRequest);
    }

    protected Page<String> findAllStationDistricts(String district, PageRequest pageRequest) {
        return repository.findAllStationDistrict(district, pageRequest);
    }
}
