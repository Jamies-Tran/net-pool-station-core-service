package net.pool.station.core.features.station.resource.resource.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EResourceStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.station.resource.Row;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.features.station.resource.resource.repository.database.StationResourceEntity;
import net.pool.station.core.features.station.resource.resource.repository.database.StationResourceEntityMapper;
import net.pool.station.core.features.station.resource.resource.repository.database.StationResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceCommandService {
    StationResourceRepository repository;

    StationResourceEntityMapper mapper;

    protected StationResource save(StationResource stationResource) {
        validate(stationResource.areaId(), stationResource);
        return mapper.toDto(repository.save(mapper.toEntity(stationResource)));
    }

    protected void save(Long areaId, StationResource stationResource) {
        if (!repository.validateToken(areaId)) {
            throw new MyAuthenticationException();
        }
        repository.save(mapper.toEntity(stationResource.withAreaId(areaId)));
    }

    protected void update(Long stationResourceId, StationResource stationResource) {
        repository.findByStationResourceIdAndDeletedFalse(stationResourceId)
                .ifPresentOrElse(
                        foundResource -> {
                            validate(foundResource.getAreaId(), stationResource, foundResource);
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

    protected Map<Long, StationResourceSpec> saveAll(Long areaId, List<StationResource> stationResources) {
        validate(areaId, stationResources);

        return stationResources.stream()
                .flatMap(sr -> {
                    StationResourceEntity saveResource = repository.save(mapper.toEntity(sr.withAreaId(areaId)));

                    return Map.of(saveResource.getStationResourceId(), sr.spec()).entrySet().stream();
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, HashMap::new));

    }

    private void validate(Long areaId, StationResource stationResource) {
        List<StationResourceEntity> existResources = repository.findAllByAreaId(areaId).stream()
                .toList();
        Map<String, List<String>> resourceRowMaps = existResources.stream()
                .collect(Collectors.groupingBy(StationResourceEntity::getRowCode,
                        Collectors.mapping(StationResourceEntity::getRowName, Collectors.toList())));
        List<String> rowNames = resourceRowMaps.computeIfAbsent(stationResource.rowCode(), k -> List.of());
        boolean repositoryCheckDuplicatedRow = !CollectionUtils.isEmpty(rowNames) && rowNames
                .stream().noneMatch(name -> MyObjectUtils.isEquals(name, stationResource.rowName()));
        if (repositoryCheckDuplicatedRow) {
            throw new MyResourceNotValid("Mã và tên dãy không hợp lệ");
        }

        boolean repositoryCheckDuplicatedCode = existResources.stream()
                .anyMatch(r -> MyObjectUtils.isEquals(r.getResourceCode(),
                        stationResource.resourceCode()));
        if (repositoryCheckDuplicatedCode) {
            throw new MyResourceNotValid("Mã tài nguyên không được trùng");
        }

        boolean repositoryCheckDuplicatedName = existResources.stream()
                .anyMatch(r -> MyObjectUtils.isEquals(r.getResourceName(),
                        stationResource.resourceName()));
        if (repositoryCheckDuplicatedName) {
            throw new MyResourceNotValid("Tên tài nguyên không được trùng");
        }
    }

    private void validate(Long areaId, StationResource stationResource, StationResourceEntity exist) {
        List<StationResourceEntity> existResources = repository.findAllByAreaId(areaId).stream()
                .filter(r -> MyObjectUtils.isNotEquals(r.getStationResourceId(),
                        exist.getStationResourceId()))
                .toList();
        Map<String, List<String>> resourceRowMaps = existResources.stream()
                .collect(Collectors.groupingBy(StationResourceEntity::getRowCode,
                        Collectors.mapping(StationResourceEntity::getRowName, Collectors.toList())));
        List<String> rowNames = resourceRowMaps.computeIfAbsent(stationResource.rowCode(), k -> List.of());
        boolean repositoryCheckDuplicatedRow = !CollectionUtils.isEmpty(rowNames) && rowNames
                .stream().noneMatch(name -> MyObjectUtils.isEquals(name, stationResource.rowName()));
        if (repositoryCheckDuplicatedRow) {
            throw new MyResourceNotValid("Mã và tên dãy không hợp lệ");
        }

        boolean repositoryCheckDuplicatedCode = existResources.stream()
                .anyMatch(r -> MyObjectUtils.isEquals(r.getResourceCode(),
                        stationResource.resourceCode()));
        if (repositoryCheckDuplicatedCode) {
            throw new MyResourceNotValid("Mã tài nguyên không được trùng");
        }

        boolean repositoryCheckDuplicatedName = existResources.stream()
                .anyMatch(r -> MyObjectUtils.isEquals(r.getResourceName(),
                        stationResource.resourceName()));
        if (repositoryCheckDuplicatedName) {
            throw new MyResourceNotValid("Tên tài nguyên không được trùng");
        }
    }

    private void validate(Long areaId, List<StationResource> stationResources) {
        List<StationResourceEntity> existResources = repository.findAllByAreaId(areaId);
        Map<String, List<String>> resourceRowMaps = existResources.stream()
                .collect(Collectors.groupingBy(StationResourceEntity::getRowCode,
                        Collectors.mapping(StationResourceEntity::getRowName, Collectors.toList())));
        Map<String, List<String>> rowMap = stationResources.stream()
                .collect(Collectors.groupingBy(StationResource::rowCode, Collectors
                        .mapping(StationResource::rowName, Collectors.toList())));
        boolean listCheckDuplicatedRow = rowMap.values().stream().anyMatch(resource -> resource.stream()
                .distinct().count() > 1);
        boolean repositoryCheckDuplicatedRow = rowMap.entrySet().stream()
                .anyMatch(entry -> {
                    List<String> rowNames = resourceRowMaps
                            .computeIfAbsent(entry.getKey(), k -> List.of());
                    if (CollectionUtils.isEmpty(rowNames)) {
                        return resourceRowMaps.entrySet().stream()
                                .anyMatch(n -> entry.getValue().stream()
                                        .anyMatch(n.getValue()::contains));
                    }
                    return rowNames.stream().noneMatch(name -> entry.getValue().contains(name));
                });
        if (listCheckDuplicatedRow || repositoryCheckDuplicatedRow) {
            throw new MyResourceNotValid("Mã và tên dãy không hợp lệ");
        }

        List<String> resourceNames = stationResources.stream()
                .map(StationResource::resourceName)
                .toList();
        List<String> resourceCodes = stationResources.stream()
                .map(StationResource::resourceCode)
                .toList();
        Set<String> resourceNameSet = new HashSet<>(resourceNames);
        Map<String, List<StationResourceEntity>> resourceNameMaps = existResources.stream()
                .collect(Collectors.groupingBy(StationResourceEntity::getResourceName));
        boolean listCheckDuplicatedName = MyObjectUtils.isNotEquals(resourceNameSet.size(), resourceNames.size());
        boolean repositoryCheckDuplicatedName = resourceNames.stream().anyMatch(name -> !CollectionUtils
                .isEmpty(resourceNameMaps.computeIfAbsent(name, k -> List.of())));
        if (listCheckDuplicatedName || repositoryCheckDuplicatedName) {
            throw new MyResourceNotValid("Tên tài nguyên không được trùng");
        }

        Set<String> resourceCodeSet = new HashSet<>(resourceCodes);
        Map<String, List<StationResourceEntity>> resourceCodeMaps = existResources.stream()
                .collect(Collectors.groupingBy(StationResourceEntity::getResourceCode));
        boolean listCheckDuplicatedCode = MyObjectUtils.isNotEquals(resourceCodeSet.size(), resourceCodes.size());
        boolean repositoryCheckDuplicatedCode = resourceCodes.stream().anyMatch(code -> !CollectionUtils
                .isEmpty(resourceCodeMaps.computeIfAbsent(code, k -> List.of())));
        if (listCheckDuplicatedCode || repositoryCheckDuplicatedCode) {
            throw new MyResourceNotValid("Mã tài nguyên không được trùng");
        }
    }
}
