package net.pool.station.core.domain.wallet;

import lombok.Builder;
import lombok.With;

@Builder
public record Wallet(
        Long walletId,
        @With
        Long accountId,
        Double balance,
        Boolean directPayment,
        String statusCode,
        String statusName
) {
        public static Wallet empty() {
                return Wallet.builder().build();
        }
}
