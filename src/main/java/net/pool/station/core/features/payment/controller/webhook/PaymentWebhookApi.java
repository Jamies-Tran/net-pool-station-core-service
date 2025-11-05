package net.pool.station.core.features.payment.controller.webhook;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.payment.controller.webhook.models.PaymentWebhookRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/payment/webhook")
public interface PaymentWebhookApi {
    @PostMapping
    MyValueResponse<?> webhook(@RequestBody PaymentWebhookRequest request);
}
