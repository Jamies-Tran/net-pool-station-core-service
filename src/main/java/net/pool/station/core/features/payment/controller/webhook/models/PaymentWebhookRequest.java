package net.pool.station.core.features.payment.controller.webhook.models;

public record PaymentWebhookRequest(
        String code,
        String desc,
        Boolean success,
        Data data,
        String signature
) {
    public record Data(
            String orderCode,
            Integer amount,
            String description,
            String accountNumber,
            String reference,
            String transactionDateTime,
            String currency,
            String paymentLinkId,
            String code,
            String desc,
            String counterAccountBankId,
            String counterAccountBankName,
            String counterAccountName,
            String counterAccountNumber,
            String virtualAccountName,
            String virtualAccountNumber
    ) {}
}
