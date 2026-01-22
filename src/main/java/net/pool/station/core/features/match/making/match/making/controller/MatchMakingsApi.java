package net.pool.station.core.features.match.making.match.making.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingRequest;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.payment.method.PaymentMethodRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/v1/api/match-making")
@PreAuthorize("hasRole('ROLE_PLAYER')")
public interface MatchMakingsApi {
    @PostMapping
    MyValueResponse<Long> save(@RequestBody @Valid MatchMakingRequest request);

    @GetMapping
    MyPageResponse<MatchMakingResponse> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam(required = false, value = "createdBy", defaultValue = "")
            String createdBy,

            @RequestParam(required = false, value = "timeRangeStartAt", defaultValue = "")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            List<LocalDate> timeRangeStartAt,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "createdAt_desc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );

    @PutMapping("/participant/empty/{matchParticipantId}")
    MyValueResponse<?> emptyParticipant(@PathVariable Long matchParticipantId);

    @PostMapping("/participant/{matchParticipateId}/wallet-payment")
    MyValueResponse<?> participantWalletPayment(@PathVariable Long matchParticipateId,
                                                @RequestBody @Valid PaymentMethodRequest request);
}
