package net.pool.station.core.features.payment.controller.webhook;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.payment.controller.webhook.models.PaymentWebhookRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentWebhookController implements PaymentWebhookApi {
    @Override
    public MyValueResponse<?> webhook(PaymentWebhookRequest request) {
        return null;
    }
}
