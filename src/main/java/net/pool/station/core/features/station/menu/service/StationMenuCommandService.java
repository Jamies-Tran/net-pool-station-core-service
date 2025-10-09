package net.pool.station.core.features.station.menu.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EMenuStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.station.menu.StationMenu;
import net.pool.station.core.features.station.menu.repository.database.StationMenuEntity;
import net.pool.station.core.features.station.menu.repository.database.StationMenuEntityMapper;
import net.pool.station.core.features.station.menu.repository.database.StationMenuRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationMenuCommandService {
    StationMenuRepository repository;

    StationMenuEntityMapper mapper;

    protected void save(StationMenu stationMenu) {
        validate(stationMenu, null);
        repository.save(mapper.toEntity(stationMenu));
    }

    protected void update(Long stationMenuId, StationMenu stationMenu) {
        repository.findByStationMenuIdAndDeletedFalse(stationMenuId)
                .ifPresentOrElse(
                        foundMenu -> {
                            validate(stationMenu, foundMenu);
                            mapper.update(foundMenu, stationMenu);
                            repository.save(foundMenu);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateStatus(Long stationMenuId, EMenuStatus status) {
        repository.findByStationMenuIdAndDeletedFalse(stationMenuId)
                .ifPresentOrElse(
                        foundMenu -> {
                            foundMenu.setStatusCode(status.getCode());
                            foundMenu.setStatusName(status.getName());
                            repository.save(foundMenu);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(Long stationMenuId) {
        repository.findByStationMenuIdAndDeletedFalse(stationMenuId)
                .ifPresent(foundMenu -> {
                    foundMenu.setDeleted(true);
                    repository.save(foundMenu);
                });
    }

    private void validate(StationMenu stationMenu, StationMenuEntity exist) {
        if (MyObjectUtils.isNotEmpty(exist)) {
            if (MyObjectUtils.isNotEquals(stationMenu.menuCode(), exist.getMenuCode())
                && repository.existsByMenuCodeAndDeletedFalse(stationMenu.menuCode())) {
                throw new MyResourceDuplicateException("Mã menu đã tồn tại");
            }
        } else {
            if (repository.existsByMenuCodeAndDeletedFalse(stationMenu.menuCode())) {
                throw new MyResourceDuplicateException("Mã menu đã tồn tại");
            }
        }
    }
}
