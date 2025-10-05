package net.pool.station.core.features.email.sending;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.email.sending.EmailSending;
import net.pool.station.core.domain.email.sending.EmailUseCase;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailUseCaseService implements EmailUseCase {
    JavaMailSender mailSender;


    @Override
    @Async("mailExecutor")
    public void sendMail(EmailSending emailSending) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(new InternetAddress(emailSending.from(), "Net Pool Station"));
            helper.setSubject(emailSending.subject());
            helper.setTo(emailSending.to());
            helper.setText(emailSending.body(), true);
            helper.setSentDate(new Date());
            mailSender.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
