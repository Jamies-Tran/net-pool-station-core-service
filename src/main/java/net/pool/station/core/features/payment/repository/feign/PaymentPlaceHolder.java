package net.pool.station.core.features.payment.repository.feign;

import net.pool.station.core.bootstrap.configuration.feign.PayOsFeignConfig;
import net.pool.station.core.bootstrap.rest.response.PayOsResponse;
import net.pool.station.core.features.payment.repository.feign.models.PaymentRequest;
import net.pool.station.core.features.payment.repository.feign.models.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "RequestPayment",
        url = "${environment.payOs.api-merchant-host}",
        configuration = PayOsFeignConfig.class
)
public interface PaymentPlaceHolder {
    @PostMapping("/v2/payment-requests")
    PayOsResponse<PaymentResponse> requestPayment(@RequestBody PaymentRequest request);
}
