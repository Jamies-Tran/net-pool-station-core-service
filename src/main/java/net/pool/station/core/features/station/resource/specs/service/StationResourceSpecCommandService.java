package net.pool.station.core.features.station.resource.specs.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.ESpecType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.features.station.resource.specs.repository.database.StationResourceSpecEntityMapper;
import net.pool.station.core.features.station.resource.specs.repository.database.StationResourceSpecRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceSpecCommandService {
    StationResourceSpecRepository repository;

    StationResourceSpecEntityMapper mapper;

    protected void save(StationResourceSpec stationResourceSpec) {

        repository.findByAreaIdAndDeletedFalse(stationResourceSpec.areaId())
                .ifPresentOrElse(
                        foundSpec -> {
                            if (MyObjectUtils.isEquals(ESpecType.PC.getCode(), foundSpec.getTypeCode())) {
                                if (!foundSpec.equals(mapper.toEntity(stationResourceSpec))) {
                                    repository.save(mapper.toEntity(stationResourceSpec));
                                }
                            }
                        },
                        () -> {
                            repository.save(mapper.toEntity(stationResourceSpec));
                        }
                );
    }

    protected void update(Long stationResourceSpecId, StationResourceSpec stationResourceSpec) {
        repository.findByStationResourceSpecIdAndDeletedFalse(stationResourceSpecId)
                .ifPresentOrElse(
                        foundSpec -> {
                            mapper.update(foundSpec, stationResourceSpec);
                            repository.save(foundSpec);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(Long stationResourceSpecId) {
        repository.findByStationResourceSpecIdAndDeletedFalse(stationResourceSpecId)
                .ifPresentOrElse(
                        foundSpec -> {
                            foundSpec.setDeleted(true);
                            repository.save(foundSpec);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }
}
