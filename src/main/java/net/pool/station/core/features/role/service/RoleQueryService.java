package net.pool.station.core.features.role.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.role.Role;
import net.pool.station.core.features.role.repository.database.RoleEntity;
import net.pool.station.core.features.role.repository.database.RoleEntityMapper;
import net.pool.station.core.features.role.repository.database.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleQueryService {
    RoleRepository repository;

    RoleEntityMapper mapper;

    protected Optional<Role> findByRoleCode(String roleCode) {
        return repository.findByRoleCode(roleCode)
                .map(mapper::toDto);
    }

    protected Optional<Role> findById(Long roleId) {
        return repository.findById(roleId)
                .map(mapper::toDto);
    }
}
