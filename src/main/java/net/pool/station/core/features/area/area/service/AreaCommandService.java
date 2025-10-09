package net.pool.station.core.features.area.area.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EAreaStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.area.Area;
import net.pool.station.core.features.area.area.repository.database.AreaEntity;
import net.pool.station.core.features.area.area.repository.database.AreaEntityMapper;
import net.pool.station.core.features.area.area.repository.database.AreaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaCommandService {
    AreaRepository repository;

    AreaEntityMapper mapper;

    protected void save(Area area) {
        validate(area, null);

        repository.save(mapper.toEntity(area));
    }

    protected void update(Long areaId, Area area) {
        repository.findByAreaIdAndDeletedFalse(areaId)
                .ifPresentOrElse(
                        foundArea -> {
                            validate(area, foundArea);
                            mapper.update(foundArea, area);

                            repository.save(foundArea);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateStatus(Long areaId, EAreaStatus status) {
        repository.findByAreaIdAndDeletedFalse(areaId)
                .ifPresentOrElse(
                        foundArea -> {
                            foundArea.setStatusCode(status.getCode());
                            foundArea.setStatusName(status.getName());
                            repository.save(foundArea);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(Long areaId) {
        repository.findByAreaIdAndDeletedFalse(areaId)
                .ifPresentOrElse(
                        foundArea -> {
                            foundArea.setDeleted(true);
                            repository.save(foundArea);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    private void validate(Area area, AreaEntity exists) {
        if (MyObjectUtils.isNotEmpty(exists)) {
            if (MyObjectUtils.isNotEquals(area.areaCode(), exists.getAreaCode())
                && repository.existsByAreaCode(area.areaCode())) {
                throw new MyResourceDuplicateException("Mã khu vực không được trùng lặp");
            }
        } else {
            if (repository.existsByAreaCode(area.areaCode())) {
                throw new MyResourceDuplicateException("Mã khu vực không được trùng lặp");
            }
        }


    }
}
