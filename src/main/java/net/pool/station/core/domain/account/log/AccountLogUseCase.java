package net.pool.station.core.domain.account.log;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface AccountLogUseCase {
    void save(AccountLog accountLog);

    Page<AccountLog> findAll(AccountLogCriteria criteria, PageRequest pageRequest);
}
