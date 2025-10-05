package net.pool.station.core.features.station.space.space.service;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EStationSpaceStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.station.space.StationSpace;
import net.pool.station.core.domain.station.space.StationSpaceId;
import net.pool.station.core.features.station.space.space.repository.database.StationSpaceEntity;
import net.pool.station.core.features.station.space.space.repository.database.StationSpaceEntityMapper;
import net.pool.station.core.features.station.space.space.repository.database.StationSpaceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceCommandService {
    StationSpaceRepository repository;

    StationSpaceEntityMapper mapper;

    protected void save(@NotNull StationSpace space) {
        validate(space.stationSpaceId().stationId(), space, null);
        repository.save(mapper.toEntity(space));
    }

    protected void update(@NotNull StationSpaceId stationSpaceId,
                          @NotNull StationSpace stationSpace)
    {
        repository.findById(stationSpaceId)
                .ifPresentOrElse(
                        foundStationSpace -> {
                            validate(stationSpaceId.stationId(), stationSpace, foundStationSpace);
                            mapper.update(foundStationSpace, stationSpace);
                            repository.save(foundStationSpace);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(@NotNull StationSpaceId stationSpaceId) {
        repository.findById(stationSpaceId)
                .ifPresentOrElse(
                        foundStationSpace -> {
                            foundStationSpace.setDeleted(true);
                            repository.save(foundStationSpace);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateStatus(
            @NotNull StationSpaceId stationSpaceId,
            @NotNull EStationSpaceStatus status
    ) {
        repository.findById(stationSpaceId)
                .ifPresentOrElse(
                        foundStationSpace -> {
                            foundStationSpace.setStatusCode(status.getCode());
                            foundStationSpace.setStatusName(status.getName());
                            repository.save(foundStationSpace);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    private void validate(Long stationId, StationSpace source, StationSpaceEntity exists) {
        if (MyObjectUtils.isNotEmpty(exists)) {
            if (MyObjectUtils.isNotEquals(source.spaceName(), exists.getSpaceName())
                && repository.existsByStationIdAndSpaceName(stationId, source.spaceName())) {
                throw new MyResourceDuplicateException("Tên space đã tồn tại trong station");
            }

            if (MyObjectUtils.isNotEquals(source.spaceCode(), exists.getSpaceCode())
                    && repository.existsByStationIdAndSpaceCode(stationId, source.spaceCode())) {
                throw new MyResourceDuplicateException("Mã space đã tồn tại trong station");
            }
        } else {
            if (repository.existsByStationIdAndSpaceName(stationId, source.spaceName())) {
                throw new MyResourceDuplicateException("Tên space đã tồn tại trong station");
            }

            if (repository.existsByStationIdAndSpaceCode(stationId, source.spaceCode())) {
                throw new MyResourceDuplicateException("Mã space đã tồn tại trong station");
            }
        }


    }
}
