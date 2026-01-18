package net.pool.station.core.features.match.making.match.making.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.making.MatchMakingUseCase;
import net.pool.station.core.domain.payment.Payment;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingRequest;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingRequestMapping;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingResponseMapping;
import net.pool.station.core.features.payment.repository.feign.models.PaymentResponse;
import net.pool.station.core.features.payment.repository.feign.models.PaymentResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingController implements MatchMakingApi {
    MatchMakingUseCase matchMakingUseCase;

    MatchMakingRequestMapping requestMapping;

    MatchMakingResponseMapping responseMapping;

    PaymentResponseMapper paymentResponseMapper;

    @Override
    public MyValueResponse<MatchMakingResponse> findById(Long matchMakingId) {
        MatchMakingResponse response = matchMakingUseCase.findById(new DomainKey<>(matchMakingId))
                .map(responseMapping::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }

    @Override
    public MyValueResponse<?> update(Long matchMakingId, MatchMakingRequest matchMakingRequest) {
        matchMakingUseCase.update(DomainKey.of(matchMakingId), requestMapping.toDto(matchMakingRequest));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long matchMakingId) {
        matchMakingUseCase.delete(DomainKey.of(matchMakingId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> start(Long matchMakingId) {
        matchMakingUseCase.start(DomainKey.of(matchMakingId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> cancel(Long matchMakingId) {
        matchMakingUseCase.cancel(DomainKey.of(matchMakingId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> finish(Long matchMakingId) {
        matchMakingUseCase.finish(DomainKey.of(matchMakingId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<PaymentResponse> depositPayment(Long matchMakingId) {
        Payment payment = matchMakingUseCase.generateDepositPayment(DomainKey.of(matchMakingId))
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(paymentResponseMapper.toModel(payment));
    }

    @Override
    public MyValueResponse<?> depositWalletPayment(Long matchMakingId) {
        matchMakingUseCase.depositWalletPayment(DomainKey.of(matchMakingId));

        return MyValueResponse.successNoData();
    }
}
