package net.pool.station.core.features.transaction.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.transaction.Transaction;
import net.pool.station.core.domain.transaction.TransactionCriteria;
import net.pool.station.core.domain.transaction.TransactionUseCase;
import net.pool.station.core.features.transaction.controller.models.TransactionResponse;
import net.pool.station.core.features.transaction.controller.models.TransactionResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionsController implements TransactionsApi {
    TransactionUseCase transactionUseCase;

    TransactionResponseMapper responseMapper;

    @Override
    public MyPageResponse<TransactionResponse> findAll(
            List<LocalDateTime> timeRange,
            List<String> paymentTypeCodes,
            List<String> paymentMethodCodes,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        TransactionCriteria criteria = TransactionCriteria
                .of(timeRange, paymentTypeCodes, paymentMethodCodes, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<TransactionResponse> responses = transactionUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
