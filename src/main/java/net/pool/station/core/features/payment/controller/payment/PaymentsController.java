package net.pool.station.core.features.payment.controller.payment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EPaymentMethod;
import net.pool.station.core.bootstrap.enums.EPaymentType;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.payment.Payment;
import net.pool.station.core.domain.payment.PaymentUseCase;
import net.pool.station.core.features.payment.controller.payment.models.PaymentResponse;
import net.pool.station.core.features.payment.controller.payment.models.PaymentResponseMapper;
import net.pool.station.core.features.payment.controller.payment.models.WalletPaymentRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentsController implements PaymentsApi {
    PaymentUseCase paymentUseCase;

    PaymentResponseMapper responseMapper;

    @Override
    public MyValueResponse<PaymentResponse> createWalletPayment(WalletPaymentRequest request) {
        Payment payment = Payment.builder()
                .amount(request.amount())
                .currency(request.currency())
                .description("Thanh toán ví NPS")
                .paymentMethodCode(EPaymentMethod.BANK_TRANSFER.getCode())
                .paymentMethodName(EPaymentMethod.BANK_TRANSFER.getName())
                .paymentTypeCode(EPaymentType.WALLET_PAYMENT.getCode())
                .paymentTypeName(EPaymentType.WALLET_PAYMENT.getName())
                .build();
        Payment createdPayment = paymentUseCase.create(payment);

        return MyValueResponse.success(responseMapper.toModel(createdPayment));
    }
}
