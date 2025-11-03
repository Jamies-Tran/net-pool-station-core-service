package net.pool.station.core.features.role.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.role.Role;
import net.pool.station.core.domain.role.RoleUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleUseCaseService implements RoleUseCase {
    RoleQueryService queryService;

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(DomainKey<Long> id) {
        return queryService.findById(id.value());
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByCode(DomainKey<String> code) {
        return queryService.findByRoleCode(code.value());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll(String search) {
        return queryService.findAll(search);
    }

}
