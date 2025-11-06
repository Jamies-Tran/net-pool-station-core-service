package net.pool.station.core.features.payment.controller.payment;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.payment.controller.payment.models.PaymentResponse;
import net.pool.station.core.features.payment.controller.payment.models.WalletPaymentRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/payment")
public interface PaymentsApi {
    @PostMapping("/wallet")
    MyValueResponse<PaymentResponse> createWalletPayment(@RequestBody @Valid WalletPaymentRequest request);
}
