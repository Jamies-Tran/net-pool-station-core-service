package net.pool.station.core.features.match.joining.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EMatchJoiningRegistrationStatus;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistration;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistrationCriteria;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistrationUseCase;
import net.pool.station.core.domain.match.participant.MatchParticipantUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchJoiningRegistrationUseCaseService implements MatchJoiningRegistrationUseCase {
    MatchJoiningRegistrationCommandService commandService;

    MatchJoiningRegistrationQueryService queryService;

    MatchParticipantUseCase matchParticipantUseCase;

    AccountUseCase accountUseCase;

    @Override
    @Transactional
    public void save(MatchJoiningRegistration matchJoiningRegistration) {
        commandService.save(matchJoiningRegistration);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchJoiningRegistration> findById(DomainKey<Long> matchJoiningRegistrationId) {
        return queryService.findById(matchJoiningRegistrationId.value())
                .map(m -> {
                    Account account = accountUseCase.findById(DomainKey.of(Long.valueOf(m.createdBy())))
                            .orElse(null);

                    return m.withAccount(account);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatchJoiningRegistration> findAll(MatchJoiningRegistrationCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> matchJoiningRegistrationId, MatchJoiningRegistration matchJoiningRegistration) {
        commandService.update(matchJoiningRegistrationId.value(), matchJoiningRegistration);
    }

    @Override
    @Transactional
    public void accept(DomainKey<Long> matchJoiningRegistrationId) {
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        Boolean isMatchMakingOwner = queryService.existsByMatchMakingCreatedBy(matchJoiningRegistrationId.value(),
                loginInfo.accountId());
        if (!isMatchMakingOwner) {
            throw new MyResourceNotValid("Bạn không thể chấp nhật yêu cầu này.");
        }
        MatchJoiningRegistration savedRegistration = commandService.updateStatus(matchJoiningRegistrationId.value(),
                EMatchJoiningRegistrationStatus.ACCEPT);
        matchParticipantUseCase.fillEmptyParticipant(DomainKey.of(savedRegistration.matchMakingId()),
                Long.valueOf(savedRegistration.createdBy()));
    }

    @Override
    @Transactional
    public void deny(DomainKey<Long> matchJoiningRegistrationId) {
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        Boolean isMatchMakingOwner = queryService.existsByMatchMakingCreatedBy(matchJoiningRegistrationId.value(),
                loginInfo.accountId());
        if (!isMatchMakingOwner) {
            throw new MyResourceNotValid("Bạn không thể từ chối yêu cầu này.");
        }
        commandService.updateStatus(matchJoiningRegistrationId.value(),
                EMatchJoiningRegistrationStatus.DENY);
    }

    @Override
    @Transactional
    public void cancel(DomainKey<Long> matchJoiningRegistrationId) {
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        Boolean isOwner = queryService.existsByCreatedBy(matchJoiningRegistrationId.value(), loginInfo.accountId());
        if (!isOwner) {
            throw new MyResourceNotValid("Bạn không thể hủy yêu cầu này.");
        }
        commandService.updateStatus(matchJoiningRegistrationId.value(),
                EMatchJoiningRegistrationStatus.CANCEL);
    }
}
