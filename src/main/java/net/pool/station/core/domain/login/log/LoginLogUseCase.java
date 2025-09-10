package net.pool.station.core.domain.login.log;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoginLogUseCase {
    void save(LoginLog loginLog);

    Page<LoginLog> findAll(LoginLogCriteria criteria, Pageable pageable);
}
