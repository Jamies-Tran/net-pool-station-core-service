package net.pool.station.core.features.login.info.repository.database;

import net.pool.station.core.features.login.info.repository.database.dao.LoginInfoDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfoEntity, Long> {
    Optional<LoginInfoEntity> findByEmail(String email);

    @Query("""
        SELECT 
                a.accountId AS accountId,
                a.email AS email,
                a.username AS username,
                r.roleCode AS roleCode    
        FROM LoginInfoEntity l
        INNER JOIN AccountEntity a ON a.accountId = l.accountId
        INNER JOIN RoleEntity r ON a.roleId = r.roleId
        WHERE l.email = :email
        """)
    Optional<LoginInfoDao> findLoginInfoByEmail(String email);

    @Query("""
        SELECT li
        FROM LoginInfoEntity li
        WHERE li.refreshToken = :refreshToken
                AND li.refreshExpiredAt > NOW()
        """)
    Optional<LoginInfoEntity> findByRefreshToken(String refreshToken);
}
