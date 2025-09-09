package net.pool.station.core.domain.email.sending;

import lombok.Builder;
import net.pool.station.core.bootstrap.configuration.common.EnvironmentVariable;
import net.pool.station.core.bootstrap.enums.EMailType;

@Builder
public record EmailSending(
        String from,
        String to,
        String content,
        Boolean isHTMLSupport,
        String subject,
        String body
) {

    public static EmailSending composeMessage(EMailType mailType, String content, String mail) {
        EmailSendingBuilder builder = EmailSending.builder();
        switch (mailType) {
            case VALIDATE_ACCOUNT -> {
                String subject = "[NPS] - Mã xác nhận tài khoản";
                String body = "<html><body><h2>Xác nhận tài khoản</h2><p>Chào bạn,</p><p>Mã xác nhận của bạn là:</p><h1 style='color: #007bff;'> %s </h1><p>Mã này có hiệu lực trong 15 phút.</p><p>Nếu bạn không yêu cầu, vui lòng bỏ qua email này.</p><br><p>Trân trọng,<br><strong>Net Pool Station</strong></p></body></html>"
                        .formatted(content);

                builder
                        .from(EnvironmentVariable.systemEmail)
                        .to(mail)
                        .subject(subject)
                        .body(body)
                        .isHTMLSupport(true);
            }
            default -> {}
        }


        return builder.build();
    }
}
