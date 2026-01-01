package net.pool.station.core.features.account.account.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountCriteria;
import net.pool.station.core.features.account.account.repository.database.AccountEntity;
import net.pool.station.core.features.account.account.repository.database.AccountEntityMapper;
import net.pool.station.core.features.account.account.repository.database.AccountRepository;
import net.pool.station.core.features.account.account.repository.database.models.StationDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountQueryService {
    AccountRepository repository;

    AccountEntityMapper mapper;

    protected Optional<Account> findByEmail(String email) {

        return repository.findByEmail(email)
                .map(foundEntity -> {
                    List<Account.Station> stations = mapper
                            .toDtos(repository.findAllStationByAccountId(foundEntity.getAccountId()));
                    return mapper.toDto(foundEntity)
                            .withStations(stations);
                });
    }

    protected Optional<Account> findById(Long accountId) {
        return repository.findByAccountId(accountId)
                .map(foundEntity -> {
                    List<Account.Station> stations = mapper
                            .toDtos(repository.findAllStationByAccountId(foundEntity.getAccountId()));
                    return mapper.toDto(foundEntity)
                            .withStations(stations);
                });
    }

    protected Page<Account> findAll(AccountCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected List<Account> findAllByAccountIdIn(List<Long> accountIds) {
        return mapper.toDto(repository.findAllByAccountIdIn(accountIds));
    }

    protected List<Account> findAllStationAdminByStationResourceId(Long stationResourceId) {
        List<AccountEntity> accounts = repository.findAllStationAdminByStationResourceId(stationResourceId);

        return mapper.toDto(accounts);
    }
}
