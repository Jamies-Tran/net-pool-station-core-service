package net.pool.station.core.features.match.making.match.making.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingRequest;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingResponse;
import net.pool.station.core.features.payment.repository.feign.models.PaymentResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/api/match-making/{matchMakingId}")
public interface MatchMakingApi {
    @GetMapping
    MyValueResponse<MatchMakingResponse> findById(@PathVariable Long matchMakingId);

    @PutMapping
    MyValueResponse<?> update(@PathVariable Long matchMakingId,
                              @RequestBody @Valid MatchMakingRequest matchMakingRequest);

    @DeleteMapping
    MyValueResponse<?> delete(@PathVariable Long matchMakingId);

    @PatchMapping("/start")
    MyValueResponse<?> start(@PathVariable Long matchMakingId);

    @PatchMapping("/cancel")
    MyValueResponse<?> cancel(@PathVariable Long matchMakingId);

    @PatchMapping("/finish")
    MyValueResponse<?> finish(@PathVariable Long matchMakingId);

    @GetMapping("/payment")
    MyValueResponse<PaymentResponse> payment(@PathVariable Long matchMakingId);

    @PostMapping("/wallet-payment")
    MyValueResponse<?> walletPayment(@PathVariable Long matchMakingId);
}
