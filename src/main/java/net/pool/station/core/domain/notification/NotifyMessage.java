package net.pool.station.core.domain.notification;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.Builder;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.fcm.info.FcmInfo;

import java.util.List;
import java.util.Map;

@Builder
public record NotifyMessage(
        String deviceType,
        String fcmToken,
        String title,
        String message,
        Map<String, String> data
) {
    public static List<NotifyMessage> of(List<FcmInfo> fcmInfos, Booking booking) {
        return fcmInfos.stream()
                .map(fcmInfo -> NotifyMessage.builder()
                        .deviceType(fcmInfo.deviceType())
                        .fcmToken(fcmInfo.fcmToken())
                        .title("Có đơn booking mới!")
                        .message("Mã số booking %s".formatted(booking.bookingCode()))
                        .data(Map.of(
                                "bookingId", booking.bookingId().toString(),
                                "screen", "BOOKING_DETAIL"
                        ))
                        .build())
                .toList();
    }

    public Message firebaseMessage() {
        return Message.builder()
                .setToken(fcmToken)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(message)
                        .build())
                .putAllData(data)
                .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                .build();
    }
}
