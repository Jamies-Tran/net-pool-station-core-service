package net.pool.station.core.domain.station.log;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface StationLogUseCase {
    void save(StationLog stationLog);

    Page<StationLog> findAll(StationLogCriteria criteria, PageRequest pageRequest);
}
