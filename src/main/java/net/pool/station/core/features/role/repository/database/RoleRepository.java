package net.pool.station.core.features.role.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleCode(String roleCode);

    @Query("""
        SELECT r
        FROM RoleEntity r
                WHERE :#{#search.empty} = TRUE
                        OR r.roleName ILIKE %:search%
        """)
    List<RoleEntity> findAll(String search);
}
