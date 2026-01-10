package net.pool.station.core.features.station.station.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EStationStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.station.Station;
import net.pool.station.core.features.station.station.repository.database.StationEntity;
import net.pool.station.core.features.station.station.repository.database.StationEntityMapper;
import net.pool.station.core.features.station.station.repository.database.StationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationCommandService {
    StationRepository repository;

    StationEntityMapper mapper;

    protected Long save(Station station) {
        validate(null, station);
        return repository.save(mapper.toEntity(station)).getStationId();
    }

    protected Station update(Long stationId, Station station) {
        Optional<StationEntity> entity = repository.findByStationId(stationId);
        return entity
                .map(foundStation -> {
                    validate(foundStation, station);
                    mapper.update(foundStation, station);

                    return mapper.toDto(repository.save(foundStation));
                })
                .orElseThrow(MyResourceNotFoundException::new);
    }

    protected void delete(Long stationId) {
        repository.findByStationId(stationId)
                .ifPresentOrElse(
                        foundStation -> {
                            validate(foundStation);
                            foundStation.setDeleted(true);
                            repository.save(foundStation);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateStatus(Long stationId, EStationStatus status, String rejectReason) {
        repository.findByStationId(stationId)
                .ifPresentOrElse(
                        foundStation -> {
                            if (MyObjectUtils.isNotEmpty(rejectReason)
                                    && MyObjectUtils.isEquals(EStationStatus.REJECT, status)) {

                                foundStation.setRejectReason(rejectReason);
                                foundStation.setRejectAt(LocalDateTime.now());
                            }
                            foundStation.setStatusCode(status.getCode());
                            foundStation.setStatusName(status.getName());
                            repository.save(foundStation);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    private void validate(StationEntity exist, Station station) {
        if (MyObjectUtils.isEmpty(exist)) {
            if (repository.existsByStationName(station.stationName())) {
                throw new MyResourceDuplicateException("Tên station đã tồn tại");
            }
        } else {
            if (!MyObjectUtils.isEquals(exist.getStationName(), station.stationName())
                && repository.existsByStationName(station.stationName())) {
                throw new MyResourceDuplicateException("Tên station đã tồn tại");
            }
        }
    }

    private void validate(StationEntity exist) {
        if (MyObjectUtils.isEquals(EStationStatus.ACTIVE.getCode(), exist.getStatusCode())) {
            throw new MyResourceNotValid("Không thể xóa station lúc này");
        }
    }
}
