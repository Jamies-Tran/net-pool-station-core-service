package net.pool.station.core.domain.role;

import net.pool.station.core.domain.DomainKey;

import java.util.Optional;

public interface RoleUseCase {
    Optional<Role> findByCode(DomainKey<String> code);

    Optional<Role> findById(DomainKey<Long> id);
}
