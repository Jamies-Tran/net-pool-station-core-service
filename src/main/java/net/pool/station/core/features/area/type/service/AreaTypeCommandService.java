package net.pool.station.core.features.area.type.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EAreaTypeStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.area.type.AreaType;
import net.pool.station.core.features.area.type.repository.database.AreaTypeEntity;
import net.pool.station.core.features.area.type.repository.database.AreaTypeEntityMapper;
import net.pool.station.core.features.area.type.repository.database.AreaTypeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaTypeCommandService {
    AreaTypeRepository repository;

    AreaTypeEntityMapper mapper;

    protected void save(AreaType areaType) {
        validate(areaType, null);

        repository.save(mapper.toEntity(areaType));
    }

    protected void update(Long areaTypeId, AreaType areaType) {
        repository.findByAreaTypeIdAndDeletedFalse(areaTypeId)
                .ifPresentOrElse(
                        foundAreaType -> {
                            validate(areaType, foundAreaType);
                            mapper.update(foundAreaType, areaType);
                            repository.save(foundAreaType);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateStatus(Long areaTypeId, EAreaTypeStatus status) {
        repository.findByAreaTypeIdAndDeletedFalse(areaTypeId)
                .ifPresentOrElse(
                        foundAreaType -> {
                            foundAreaType.setStatusCode(status.getCode());
                            foundAreaType.setStatusName(status.getName());
                            repository.save(foundAreaType);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(Long areaTypeId) {
        repository.findByAreaTypeIdAndDeletedFalse(areaTypeId)
                .ifPresentOrElse(
                        foundAreaType -> {
                            foundAreaType.setDeleted(true);
                            repository.save(foundAreaType);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    private void validate(AreaType areaType, AreaTypeEntity exists) {
        if (MyObjectUtils.isNotEmpty(exists)) {
            if (!MyObjectUtils.isEquals(areaType.typeCode(), exists.getTypeCode())
                && repository.existsByTypeCode(areaType.typeCode())) {
                throw new MyResourceDuplicateException("Mã loại khu vực đã tồn tại");
            }

            if (!MyObjectUtils.isEquals(areaType.typeName(), exists.getTypeName())
                    && repository.existsByTypeName(areaType.typeName())) {
                throw new MyResourceDuplicateException("Tên loại khu vực đã tồn tại");
            }
        }

        if (repository.existsByTypeCode(areaType.typeCode())) {
            throw new MyResourceDuplicateException("Mã loại khu vực đã tồn tại");
        }

        if (repository.existsByTypeName(areaType.typeName())) {
            throw new MyResourceDuplicateException("Tên loại khu vực đã tồn tại");
        }
    }
}
