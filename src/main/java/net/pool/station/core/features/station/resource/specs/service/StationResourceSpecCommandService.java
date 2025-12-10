package net.pool.station.core.features.station.resource.specs.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
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
        String resourceTypeCode = repository.findStationResourceTypeById(stationResourceSpec
                .stationResourceId());
        if (MyObjectUtils.isNotEquals(resourceTypeCode, stationResourceSpec.typeCode())) {
            throw new MyResourceNotValid("Cấu hình không hợp lệ");
        }
        repository.findByStationResourceIdAndDeletedFalse(stationResourceSpec.stationResourceId())
                        .ifPresentOrElse(
                                foundEntity -> {
                                    mapper.update(foundEntity, stationResourceSpec);
                                    repository.save(foundEntity);
                                },
                                () -> {
                                    repository.save(mapper.toEntity(stationResourceSpec));
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
