package net.pool.station.core.features.transaction.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.features.transaction.controller.models.TransactionResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/v1/api/transactions")
public interface TransactionsApi {
    @GetMapping
    @PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_PLAYER'})")
    MyPageResponse<TransactionResponse> findAll(
            @RequestParam(required = false, value = "timeRange", defaultValue = "")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            List<LocalDateTime> timeRange,

            @RequestParam(required = false, value = "paymentTypeCodes", defaultValue = "")
            List<String> paymentTypeCodes,

            @RequestParam(required = false, value = "paymentMethodCodes", defaultValue = "")
            List<String> paymentMethodCodes,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "createdAt_desc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
