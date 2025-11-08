package net.pool.station.core.features.payment.controller.webhook;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.features.payment.controller.webhook.models.PaymentWebhookRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentWebhookController implements PaymentWebhookApi {
    @Override
    public MyValueResponse<?> webhook(PaymentWebhookRequest request) {
        log.info("Payment webhook received: {}", request);
        if (MyObjectUtils.isEmpty(request.signature()) && MyObjectUtils.isEquals(request.code(), "00")) {
            return MyValueResponse.successNoData();
        }

        return MyValueResponse.successNoData();
    }
}
