package net.pool.station.core.bootstrap.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.pool.station.core.domain.match.making.MatchMaking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyPaymentUtils {
    static Integer deposit;

    static Integer commission;

    @Value("${environment.deposit.percent:30}")
    public void setDeposit(Integer deposit) {
        MyPaymentUtils.deposit = deposit;
    }

    @Value("${environment.commission.percent:5}")
    public void setCommission(Integer commission) {
        MyPaymentUtils.commission = commission;
    }

    public static Integer calculateDeposit(MatchMaking matchMaking) {
        int totalDeposit = matchMaking.totalPrice() * deposit / 100;
        int numberOfHoldingDay = matchMaking.numberOfHoldingDay();
        if (numberOfHoldingDay > 1 && numberOfHoldingDay <= 3) {
            return (int) (totalDeposit * 1.3);
        }
        if (numberOfHoldingDay > 3 && numberOfHoldingDay <= 7) {
            return (int) (totalDeposit * 1.7);
        }

        return (int) (totalDeposit * 1.0);
    }

    public static Integer calculateCommission(Integer receive) {
        return receive * commission / 100;
    }
}
