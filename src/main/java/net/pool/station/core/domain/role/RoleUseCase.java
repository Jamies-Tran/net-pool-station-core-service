package net.pool.station.core.domain.role;

import net.pool.station.core.domain.DomainCode;

import java.util.Optional;

public interface RoleUseCase {
    Optional<Role> findByCode(DomainCode<String> code);

    Optional<Role> findById(DomainCode<Long> id);
}
