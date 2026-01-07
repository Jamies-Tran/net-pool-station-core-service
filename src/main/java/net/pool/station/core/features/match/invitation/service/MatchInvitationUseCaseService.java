package net.pool.station.core.features.match.invitation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.enums.EMatchInvitationStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantStatus;
import net.pool.station.core.bootstrap.utils.MyAuthorizationUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.fcm.info.FcmInfo;
import net.pool.station.core.domain.fcm.info.FcmInfoUseCase;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.match.invitation.MatchInvitation;
import net.pool.station.core.domain.match.invitation.MatchInvitationUseCase;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.match.participant.MatchParticipantUseCase;
import net.pool.station.core.domain.notification.Notification;
import net.pool.station.core.domain.notification.NotificationUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchInvitationUseCaseService implements MatchInvitationUseCase {
    MatchInvitationCommandService commandService;

    NotificationUseCase notificationUseCase;

    FcmInfoUseCase fcmInfoUseCase;

    MatchParticipantUseCase matchParticipantUseCase;

    @Override
    @Transactional
    public void saveAll(DomainKey<Long> matchMakingId, List<MatchInvitation> matchInvitations) {
        List<MatchInvitation> saveInvitations = commandService.saveAll(matchMakingId.value(), matchInvitations);

        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        List<Long> accounts = saveInvitations.stream().map(MatchInvitation::accountId).toList();
        List<FcmInfo> fcmInfos = fcmInfoUseCase.findAllByAccountIdIn(accounts);
        List<Notification> notifications = Notification.ofMatchInvitation(fcmInfos, saveInvitations,
                loginInfo.username());
        notificationUseCase.pushNotification(notifications);
    }

    @Override
    @Transactional
    public void accept(DomainKey<Long> matchInvitationId) {
        MatchInvitation matchInvitation = commandService.updateStatus(matchInvitationId.value(),
                EMatchInvitationStatus.ACCEPTED);
        matchParticipantUseCase.fillEmptyParticipant(DomainKey.of(matchInvitation.matchMakingId()),
                matchInvitation.accountId());
    }

    @Override
    @Transactional
    public void deny(DomainKey<Long> matchInvitationId) {
        commandService.updateStatus(matchInvitationId.value(),
                EMatchInvitationStatus.DENIED);
    }


}
