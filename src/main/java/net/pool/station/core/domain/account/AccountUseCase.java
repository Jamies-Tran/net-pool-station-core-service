package net.pool.station.core.domain.account;

import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.bootstrap.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountUseCase {
    Optional<Account> findByEmail(DomainKey<String> email);

    void save(Account account, ERole role);

    void save(Long stationId, Account account, ERole role);

    Page<Account> findAll(AccountCriteria criteria, PageRequest pageRequest);

    Optional<Account> findById(DomainKey<Long> accountId);

    List<Account> findAllByIdIn(List<Long> accountIds);

    void update(DomainKey<Long> accountId, Account account);

    void enable(DomainKey<Long> accountId);

    void disable(DomainKey<Long> accountId);

    void activate(DomainKey<Long> accountId);

    void delete(DomainKey<Long> accountId);
}
