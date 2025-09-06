package net.pool.station.core.features.login.info.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfoEntity, Long> {
    Optional<LoginInfoEntity> findByEmail(String email);

    @Query("""
        SELECT li
        FROM LoginInfoEntity li
        WHERE li.refreshToken = :refreshToken
                AND li.refreshExpiredAt > NOW()
        """)
    Optional<LoginInfoEntity> findByRefreshToken(String refreshToken);
}
