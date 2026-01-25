package net.pool.station.core.features.match.invitation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EMatchInvitationStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantStatus;
import net.pool.station.core.bootstrap.utils.MyAuthorizationUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.fcm.info.FcmInfo;
import net.pool.station.core.domain.fcm.info.FcmInfoUseCase;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.match.invitation.MatchInvitation;
import net.pool.station.core.domain.match.invitation.MatchInvitationCriteria;
import net.pool.station.core.domain.match.invitation.MatchInvitationUseCase;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.match.participant.MatchParticipantUseCase;
import net.pool.station.core.domain.notification.Notification;
import net.pool.station.core.domain.notification.NotificationUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchInvitationUseCaseService implements MatchInvitationUseCase {
    MatchInvitationCommandService commandService;

    MatchInvitationQueryService queryService;

    NotificationUseCase notificationUseCase;

    FcmInfoUseCase fcmInfoUseCase;

    MatchParticipantUseCase matchParticipantUseCase;

    AccountUseCase accountUseCase;

    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    @Transactional
    public void saveAll(DomainKey<Long> matchMakingId, List<MatchInvitation> matchInvitations) {
        if (!queryService.allowInvitationByMatchMakingId(matchMakingId.value())) {
            throw new MyResourceNotValid("Bạn không thể mời người chơi khác vào lúc này.");
        }
        commandService.saveAll(matchMakingId.value(), matchInvitations);
        reload(matchMakingId.value());
    }

    @Override
    @Transactional
    public void accept(DomainKey<Long> matchInvitationId) {
        MatchInvitation matchInvitation = commandService.updateStatus(matchInvitationId.value(),
                EMatchInvitationStatus.ACCEPTED);
        matchParticipantUseCase.fillEmptyParticipant(DomainKey.of(matchInvitation.matchMakingId()),
                matchInvitation.accountId());
        reload(matchInvitation.matchMakingId());
    }

    @Override
    @Transactional
    public void deny(DomainKey<Long> matchInvitationId) {
        MatchInvitation matchInvitation = commandService.updateStatus(matchInvitationId.value(),
                EMatchInvitationStatus.DENIED);
        reload(matchInvitation.matchMakingId());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatchInvitation> findAll(MatchInvitationCriteria criteria, PageRequest pageRequest) {
        Page<MatchInvitation> matchInvitations = queryService.findAll(criteria, pageRequest);
        List<Long> accountHostIds = matchInvitations.stream()
                .map(m -> Long.valueOf(m.createdBy()))
                .toList();
        Map<Long, Account> accounts = accountUseCase.findAllByIdIn(accountHostIds)
                .stream()
                .collect(Collectors.toMap(Account::accountId, Function.identity()));

        return matchInvitations.map(m -> m
                .withAccountHost(accounts.computeIfAbsent(Long.valueOf(m.createdBy()), k -> null)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchInvitation> findAllByMatchMakingId(DomainKey<Long> matchMakingId) {
        return queryService.findAllByMatchMakingId(matchMakingId.value());
    }

    private void reload(Long matchMakingId) {
        List<MatchInvitation> currentInvitations = queryService.findAllByMatchMakingId(matchMakingId);
        simpMessagingTemplate.convertAndSend("/topic/match-making/" + matchMakingId + "/invitations",
                currentInvitations);
    }
}
