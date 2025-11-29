package net.pool.station.core.features.space.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.ESpaceStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.space.Space;
import net.pool.station.core.features.space.repository.database.SpaceEntity;
import net.pool.station.core.features.space.repository.database.SpaceEntityMapper;
import net.pool.station.core.features.space.repository.database.SpaceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpaceCommandService {
    SpaceRepository repository;

    SpaceEntityMapper mapper;

    protected void save(Space space) {
        validate(space, null);

        repository.save(mapper.toEntity(space));
    }

    protected void update(Long spaceId, Space space) {
        repository.findBySpaceId(spaceId)
                .ifPresentOrElse(
                        foundSpace -> {
                            validate(space, foundSpace);
                            mapper.update(foundSpace, space);
                            repository.save(foundSpace);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateStatus(Long spaceId, ESpaceStatus status) {
        repository.findBySpaceId(spaceId)
                .ifPresentOrElse(
                        foundSpace -> {
                            foundSpace.setStatusCode(status.getCode());
                            foundSpace.setStatusName(status.getName());
                            repository.save(foundSpace);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(Long spaceId) {
        repository.findBySpaceId(spaceId)
                .ifPresentOrElse(
                        foundSpace -> {
                            foundSpace.setDeleted(true);
                            repository.save(foundSpace);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    private void validate(Space space, SpaceEntity exist) {
        if (MyObjectUtils.isNotEmpty(exist)) {
            if (MyObjectUtils.isNotEquals(space.typeCode(), exist.getTypeCode())
                && repository.existsByTypeCode(space.typeCode())) {
                throw new MyResourceDuplicateException("Mã space không được trùng");
            }

            if (MyObjectUtils.isNotEquals(space.typeName(), exist.getTypeName())
                    && repository.existsByTypeName(space.typeName())) {
                throw new MyResourceDuplicateException("Tên space không được trùng");
            }
        } else {
            if (repository.existsByTypeCode(space.typeCode())) {
                throw new MyResourceDuplicateException("Mã space không được trùng");
            }

            if (repository.existsByTypeName(space.typeName())) {
                throw new MyResourceDuplicateException("Tên space không được trùng");
            }
        }


    }
}
