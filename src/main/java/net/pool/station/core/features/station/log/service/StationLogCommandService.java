package net.pool.station.core.features.station.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.log.StationLog;
import net.pool.station.core.features.station.log.repository.database.StationLogEntityMapper;
import net.pool.station.core.features.station.log.repository.database.StationLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationLogCommandService {
    StationLogRepository repository;

    StationLogEntityMapper mapper;

    protected void save(StationLog stationLog) {
        repository.save(mapper.toEntity(stationLog));
    }
}
