package net.pool.station.core.domain.station;

import net.pool.station.core.domain.DomainCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StationUseCase {
    void save(Station station);

    Optional<Station> findById(DomainCode<Long> stationId);

    Page<Station> findAll(StationCriteria criteria ,Pageable pageable);

    void update(DomainCode<Long> stationId, Station station);

    void delete(DomainCode<Long> stationId);

    void accept(DomainCode<Long> stationId);

    void reject(DomainCode<Long> stationId, String rejectReason);

    void disable(DomainCode<Long> stationId);

    void enable(DomainCode<Long> stationId);

    Page<String> findAllStationProvince(String province, Pageable pageable);

    Page<String> findAllStationCommune(String commune, Pageable pageable);

    Page<String> findAllStationDistrict(String district, Pageable pageable);
}
