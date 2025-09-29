package net.pool.station.core.features.station.station.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.bootstrap.enums.EStationStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.logging.factory.LoggingFactory;
import net.pool.station.core.domain.station.Station;
import net.pool.station.core.domain.station.StationCriteria;
import net.pool.station.core.domain.station.StationUseCase;
import net.pool.station.core.domain.station.log.StationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationUseCaseService implements StationUseCase {
    StationCommandService commandService;

    StationQueryService queryService;

    LoggingFactory<StationLog> loggingService;

    @Override
    @Transactional
    public void save(Station station) {
        Long savedId = commandService.save(station);

        loggingService.log(StationLog.createSave(savedId));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Station> findById(DomainKey<Long> stationId) {
        return queryService.findById(stationId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Station> findAll(StationCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> stationId, Station station) {
        commandService.update(stationId.value(), station);

        loggingService.log(StationLog.createUpdate(stationId.value()));
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> stationId) {
        commandService.delete(stationId.value());
    }

    @Override
    @Transactional
    public void accept(DomainKey<Long> stationId) {
        commandService.updateStatus(stationId.value(), EStationStatus.ACTIVE, null);

        loggingService.log(StationLog.createAccept(stationId.value()));
    }

    @Override
    @Transactional
    public void reject(DomainKey<Long> stationId, String rejectReason) {
        commandService.updateStatus(stationId.value(), EStationStatus.REJECT, rejectReason);

        loggingService.log(StationLog.createReject(stationId.value(), rejectReason));
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> stationId) {
        commandService.updateStatus(stationId.value(), EStationStatus.INACTIVE, null);

        loggingService.log(StationLog.createInActive(stationId.value()));
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> stationId) {
        commandService.updateStatus(stationId.value(), EStationStatus.ACTIVE, null);

        loggingService.log(StationLog.createActive(stationId.value()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> findAllStationProvince(String province, Pageable pageable) {
        return queryService.findAllStationProvinces(province, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> findAllStationCommune(String commune, Pageable pageable) {
        return queryService.findAllStationCommunes(commune, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> findAllStationDistrict(String district, Pageable pageable) {
        return queryService.findAllStationDistricts(district, pageable);
    }
}
