package net.pool.station.core.features.match.making.match.making.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EMatchMakingStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.domain.match.making.MatchMakingCriteria;
import net.pool.station.core.domain.match.making.MatchMakingUseCase;
import net.pool.station.core.domain.match.making.resource.MatchMakingResource;
import net.pool.station.core.domain.match.making.resource.MatchMakingResourceUseCase;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlotUseCase;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.match.participant.MatchParticipantUseCase;
import net.pool.station.core.domain.payment.Payment;
import net.pool.station.core.domain.payment.PaymentUseCase;
import net.pool.station.core.domain.wallet.WalletUseCase;
import net.pool.station.core.features.match.making.job.ExpiredMatchMakingJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingUseCaseService implements MatchMakingUseCase {
    MatchMakingCommandService commandService;

    MatchMakingQueryService queryService;

    MatchMakingSlotUseCase slotUseCase;

    MatchMakingResourceUseCase resourceUseCase;

    MatchParticipantUseCase matchParticipantUseCase;

    PaymentUseCase paymentUseCase;

    Scheduler scheduler;

    @Override
    @Transactional
    public Long save(MatchMaking matchMaking) {
        MatchMaking savedMatchMaking = commandService.save(matchMaking);
        Long matchMakingId = savedMatchMaking.matchMakingId();
        slotUseCase.save(DomainKey.of(matchMakingId), matchMaking.slots());
        resourceUseCase.save(DomainKey.of(matchMakingId), matchMaking.resources());
        setupSchedule(savedMatchMaking);

        return savedMatchMaking.matchMakingId();
    }

    private void setupSchedule(MatchMaking matchMaking) {
        try {
            JobDetail endMatchMakingDetail = JobBuilder.newJob(ExpiredMatchMakingJob.class)
                    .withIdentity("endMatchMaking_%s".formatted(matchMaking.matchMakingId()), "matchMaking")
                    .usingJobData("matchMakingId", matchMaking.matchMakingId())
                    .build();
            Trigger endMatchMakingTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("endMatchMaking_%s".formatted(matchMaking.matchMakingId()), "matchMaking")
                    .startAt(Timestamp.valueOf(matchMaking.expiredAt().atStartOfDay()))
                    .build();
            Map<JobDetail, Set<? extends Trigger>> jobAndTrigger = new LinkedHashMap<>();
            jobAndTrigger.put(endMatchMakingDetail, Set.of(endMatchMakingTrigger));
            scheduler.scheduleJobs(jobAndTrigger, true);
        } catch (Exception e) {
            log.error("[MatchMakingUseCaseService.setupSchedule(...)] message: {}", e.getMessage(), e);
        }

    }

    @Override
    @Transactional
    public void update(DomainKey<Long> matchMakingId, MatchMaking matchMaking) {
        commandService.update(matchMakingId.value(), matchMaking);
    }

    @Override
    @Transactional
    public void process(DomainKey<Long> matchMakingId) {
        MatchMaking matchMaking = commandService.updateStatus(matchMakingId.value(), EMatchMakingStatus.PENDING);
        List<MatchParticipant> matchParticipantEmptyList = MatchParticipant
                .ofEmptyList(matchMaking.limitParticipant());

        matchParticipantUseCase.save(DomainKey.of(matchMaking.matchMakingId()), matchParticipantEmptyList);
    }

    @Override
    @Transactional
    public void start(DomainKey<Long> matchMakingId) {
        commandService.updateStatus(matchMakingId.value(), EMatchMakingStatus.STARTED);
    }

    @Override
    @Transactional
    public void cancel(DomainKey<Long> matchMakingId) {
        commandService.updateStatus(matchMakingId.value(), EMatchMakingStatus.CANCEL);
    }

    @Override
    @Transactional
    public void finish(DomainKey<Long> matchMakingId) {
        commandService.updateStatus(matchMakingId.value(), EMatchMakingStatus.FINISHED);
    }

    @Override
    @Transactional
    public void handleExpiredJob(DomainKey<Long> matchMakingId) {
        commandService.handleExpiredJob(matchMakingId.value());
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> matchMakingId) {
        commandService.delete(matchMakingId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchMaking> findById(DomainKey<Long> matchMakingId) {
        return queryService.findById(matchMakingId.value())
                .map(m -> {
                    Long walletId = queryService.findOwnerWalletIdByStationId(m.stationId())
                            .orElseThrow(MyResourceNotFoundException::new);
                    List<MatchMakingResource> resources = resourceUseCase
                            .findAllByMatchMakingId(DomainKey.of(m.matchMakingId()));
                    List<MatchMakingSlot> slots = slotUseCase
                            .findAllByMatchMakingId(DomainKey.of(m.matchMakingId()));

                    return m.withResources(resources).withSlots(slots).withWalletId(walletId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatchMaking> findAll(MatchMakingCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public Optional<Payment> generatePayment(DomainKey<Long> matchMakingId) {
        return findById(matchMakingId)
                .map(paymentUseCase::createFromMatchMaking);
    }
}
