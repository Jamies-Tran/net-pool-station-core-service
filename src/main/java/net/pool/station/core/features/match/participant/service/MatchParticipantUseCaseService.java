package net.pool.station.core.features.match.participant.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EMatchParticipantReadyStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.match.participant.MatchParticipantCancel;
import net.pool.station.core.domain.match.participant.MatchParticipantCriteria;
import net.pool.station.core.domain.match.participant.MatchParticipantUseCase;
import net.pool.station.core.domain.wallet.Wallet;
import net.pool.station.core.domain.wallet.WalletUseCase;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchParticipantUseCaseService implements MatchParticipantUseCase {
    MatchParticipantCommandService commandService;

    MatchParticipantQueryService queryService;

    WalletUseCase walletUseCase;

    AccountUseCase accountUseCase;

    @Override
    @Transactional
    public void save(DomainKey<Long> matchMakingId, List<MatchParticipant> matchParticipants) {
        commandService.save(matchMakingId.value(), matchParticipants);
    }

    @Override
    @Transactional
    public void fillEmptyParticipant(DomainKey<Long> matchMakingId, Long accountId) {
        commandService.update(matchMakingId.value(), accountId);
    }

    @Override
    @Transactional
    public MatchParticipantCancel emptyFilledParticipant(DomainKey<Long> matchParticipantId) {
        return commandService.empty(matchParticipantId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchParticipant> findAllByMatchMakingId(DomainKey<Long> matchMakingId) {
        List<MatchParticipant> participants = queryService.findAllByMatchMakingId(matchMakingId.value());
        List<Long> accountId = participants.stream().map(MatchParticipant::accountId).toList();
        Map<Long, Account> accountMap = accountMap(accountId);
        return participants.stream()
                .map(p -> {
                    Account account = accountMap.computeIfAbsent(p.accountId(), k -> null);
                    return p.withAccount(account);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatchParticipant> findAll(MatchParticipantCriteria criteria, PageRequest pageRequest) {
        Page<MatchParticipant> participants = queryService.findAll(criteria, pageRequest);
        List<Long> accountIds = participants.stream().map(MatchParticipant::accountId).toList();
        Map<Long, Account> accountMap = accountMap(accountIds);

        return participants.map(p -> p
                .withAccount(accountMap.computeIfAbsent(p.accountId(), k -> null)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchParticipant> findById(DomainKey<Long> matchParticipantId) {
        return queryService.findById(matchParticipantId.value())
                .map(m -> {
                    Wallet wallet = walletUseCase.findByAccountId(DomainKey.of(m.accountId()))
                            .orElseThrow(() -> new MyResourceNotFoundException("Không tìm thấy System Wallet của player"));
                    return m.withParticipantWalletId(wallet.walletId());
                });
    }

    @Override
    @Transactional
    public void ready(DomainKey<Long> matchParticipantId, LocalDateTime paidShareAt) {
        commandService.updateReadyStatus(matchParticipantId.value(),
                EMatchParticipantReadyStatus.READY, paidShareAt);
    }

    private Map<Long, Account> accountMap(List<Long> accountIds) {
        return accountUseCase.findAllByIdIn(accountIds)
                .stream()
                .collect(Collectors.toMap(Account::accountId, Function.identity()));
    }
}
