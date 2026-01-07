package net.pool.station.core.domain.notification;

import com.google.firebase.messaging.Message;
import lombok.Builder;
import net.pool.station.core.bootstrap.enums.ENotificationType;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.fcm.info.FcmInfo;
import net.pool.station.core.domain.match.invitation.MatchInvitation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Builder
public record Notification(
        Long notificationId,
        Long accountId,
        String title,
        String description,
        Map<String, String> payload,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        String deviceType,
        String fcmToken
) {
    public static List<Notification> ofBooking(List<FcmInfo> fcmInfos, Booking booking) {
        return fcmInfos.stream()
                .map(fcmInfo -> Notification.builder()
                        .deviceType(fcmInfo.deviceType())
                        .fcmToken(fcmInfo.fcmToken())
                        .title("Có đơn booking mới!")
                        .description("Mã số booking %s".formatted(booking.bookingCode()))
                        .payload(Map.of(
                                "bookingId", booking.bookingId().toString(),
                                "screen", "BOOKING_DETAIL"
                        ))
                        .typeCode(ENotificationType.BOOKING.getCode())
                        .typeName(ENotificationType.BOOKING.getName())
                        .build())
                .toList();
    }

    public static List<Notification> ofMatchInvitation(List<FcmInfo> fcmInfos, List<MatchInvitation> matchInvitations,
                                                       String hostName) {
        return fcmInfos.stream()
                .flatMap(fcmInfo -> matchInvitations.stream()
                                .map(invitation -> Notification.builder()
                                        .deviceType(fcmInfo.deviceType())
                                        .fcmToken(fcmInfo.fcmToken())
                                        .title("Bạn có lời mời tham gia ghép trận từ %s".formatted(hostName))
                                        .description(Optional.ofNullable(invitation.message())
                                                .orElse("Hãy cùng tham gia và kiếm thật nhiều niềm vui với mọi người"))
                                        .payload(Map.of(
                                                "matchMakingId", invitation.matchMakingId().toString(),
                                                "screen", "MATCH_MAKING_DETAIL"
                                        ))
                                        .typeCode(ENotificationType.MATCH_MAKING.getCode())
                                        .typeName(ENotificationType.BOOKING.getName())
                                        .build()))
                .toList();
    }

    public Message firebaseMessage() {
        return Message.builder()
                .setToken(fcmToken)
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(description)
                        .build())
                .putAllData(payload)
                .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                .build();
    }
}
