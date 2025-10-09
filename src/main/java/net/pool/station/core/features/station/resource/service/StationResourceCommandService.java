package net.pool.station.core.features.station.resource.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EResourceStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.features.station.resource.repository.database.StationResourceEntity;
import net.pool.station.core.features.station.resource.repository.database.StationResourceEntityMapper;
import net.pool.station.core.features.station.resource.repository.database.StationResourceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceCommandService {
    StationResourceRepository repository;

    StationResourceEntityMapper mapper;

    protected void save(StationResource stationResource) {
        validate(stationResource, null);
        repository.save(mapper.toEntity(stationResource));
    }

    protected void update(Long stationResourceId, StationResource stationResource) {
        repository.findByStationResourceIdAndDeletedFalse(stationResourceId)
                .ifPresentOrElse(
                        foundResource -> {
                            validate(stationResource, foundResource);
                            mapper.update(foundResource, stationResource);
                            repository.save(foundResource);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateStatus(Long stationResourceId, EResourceStatus status) {
        repository.findByStationResourceIdAndDeletedFalse(stationResourceId)
                .ifPresentOrElse(
                        foundResource -> {
                            foundResource.setStatusCode(status.getCode());
                            foundResource.setStatusName(status.getName());
                            repository.save(foundResource);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(Long stationResourceId) {
        repository.findByStationResourceIdAndDeletedFalse(stationResourceId)
                .ifPresentOrElse(
                        foundResource -> {
                            foundResource.setDeleted(true);
                            repository.save(foundResource);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    private void validate(StationResource stationResource, StationResourceEntity exists) {
        if (MyObjectUtils.isNotEmpty(exists)) {
            if (MyObjectUtils.isNotEquals(stationResource.resourceCode(), exists.getResourceCode())
                && repository.existsByAreaIdAndResourceCodeAndDeletedFalse(stationResource.areaId(), stationResource.resourceCode())) {
                throw new MyResourceDuplicateException("Mã đã tồn tại");
            }
        } else {
            if (repository.existsByAreaIdAndResourceCodeAndDeletedFalse(stationResource.areaId(), stationResource.resourceCode())) {
                throw new MyResourceDuplicateException("Mã đã tồn tại");
            }
        }
    }
}
