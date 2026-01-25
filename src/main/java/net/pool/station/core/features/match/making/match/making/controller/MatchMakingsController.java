package net.pool.station.core.features.match.making.match.making.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EPaymentMethod;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.making.MatchMakingCriteria;
import net.pool.station.core.domain.match.making.MatchMakingUseCase;
import net.pool.station.core.domain.payment.Payment;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingRequest;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingRequestMapping;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingResponseMapping;
import net.pool.station.core.features.match.making.match.making.controller.models.payment.method.PaymentMethodRequest;
import net.pool.station.core.features.payment.controller.payment.models.PaymentResponse;
import net.pool.station.core.features.payment.controller.payment.models.PaymentResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingsController implements MatchMakingsApi {
    MatchMakingUseCase matchMakingUseCase;

    MatchMakingRequestMapping requestMapping;

    MatchMakingResponseMapping responseMapping;

    PaymentResponseMapper paymentResponseMapper;

    @Override
    public MyValueResponse<Long> save(MatchMakingRequest request) {
        Long matchMakingId = matchMakingUseCase.save(requestMapping.toDto(request));

        return MyValueResponse.success(matchMakingId);
    }

    @Override
    public MyPageResponse<MatchMakingResponse> findAll(
            String search,
            String createdBy,
            List<LocalDate> timeRangeStartAt,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        MatchMakingCriteria criteria = MatchMakingCriteria.builder()
                .search(search)
                .createdBy(createdBy)
                .timeRangeStartAt(timeRangeStartAt)
                .statusCodes(statusCodes)
                .build();
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<MatchMakingResponse> responses = matchMakingUseCase.findAll(criteria, pageRequest)
                .map(responseMapping::toModel);

        return MyPageResponse.success(responses);
    }

    @Override
    public MyValueResponse<?> emptyParticipant(Long matchParticipantId) {
        matchMakingUseCase.emptyParticipant(DomainKey.of(matchParticipantId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<PaymentResponse> participantPayment(Long matchParticipateId, PaymentMethodRequest request) {
        EPaymentMethod paymentMethod = EPaymentMethod.valueOf(request.paymentMethodCode());
        Payment payment = matchMakingUseCase.participantPayment(DomainKey.of(matchParticipateId), paymentMethod);

        return MyValueResponse.success(paymentResponseMapper.toModel(payment));
    }
}
