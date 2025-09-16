package net.pool.station.core.features.station.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.log.StationLog;
import net.pool.station.core.domain.station.log.StationLogCriteria;
import net.pool.station.core.features.station.log.repository.database.StationLogEntityMapper;
import net.pool.station.core.features.station.log.repository.database.StationLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationLogQueryService {
    StationLogRepository repository;

    StationLogEntityMapper mapper;

    protected Page<StationLog> findAll(StationLogCriteria criteria, Pageable pageable) {
        return repository.findAll(criteria, pageable)
                .map(mapper::toDto);
    }
}
