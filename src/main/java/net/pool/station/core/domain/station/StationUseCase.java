package net.pool.station.core.domain.station;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StationUseCase {
    void save(Station station);

    Optional<Station> findById(DomainKey<Long> stationId);

    Page<Station> findAll(StationCriteria criteria, PageRequest pageRequest);

    void update(DomainKey<Long> stationId, Station station);

    void delete(DomainKey<Long> stationId);

    void accept(DomainKey<Long> stationId);

    void reject(DomainKey<Long> stationId, String rejectReason);

    void disable(DomainKey<Long> stationId);

    void enable(DomainKey<Long> stationId);

    Page<String> findAllStationProvince(String province, PageRequest pageRequest);

    Page<String> findAllStationCommune(String commune, PageRequest pageRequest);

    Page<String> findAllStationDistrict(String district, PageRequest pageRequest);
}
