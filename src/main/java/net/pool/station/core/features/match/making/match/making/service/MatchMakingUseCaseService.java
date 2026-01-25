package net.pool.station.core.features.match.making.match.making.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EMatchMakingStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantReadyStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantStatus;
import net.pool.station.core.bootstrap.enums.EPaymentMethod;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.domain.match.making.MatchMakingCriteria;
import net.pool.station.core.domain.match.making.MatchMakingUseCase;
import net.pool.station.core.domain.match.making.resource.MatchMakingResource;
import net.pool.station.core.domain.match.making.resource.MatchMakingResourceUseCase;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlotUseCase;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.match.participant.MatchParticipantCancel;
import net.pool.station.core.domain.match.participant.MatchParticipantUseCase;
import net.pool.station.core.domain.match.schedule.MatchSchedule;
import net.pool.station.core.domain.match.schedule.MatchScheduleUseCase;
import net.pool.station.core.domain.notification.NotificationUseCase;
import net.pool.station.core.domain.payment.Payment;
import net.pool.station.core.domain.payment.PaymentUseCase;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.schedule.ScheduleUseCase;
import net.pool.station.core.domain.station.resource.StationResourceUseCase;
import net.pool.station.core.domain.transaction.Transaction;
import net.pool.station.core.domain.transaction.TransactionUseCase;
import net.pool.station.core.features.match.making.job.ExpiredMatchMakingJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    ScheduleUseCase scheduleUseCase;

    Scheduler scheduler;

    StationResourceUseCase stationResourceUseCase;

    MatchScheduleUseCase matchScheduleUseCase;

    TransactionUseCase transactionUseCase;

    NotificationUseCase notificationUseCase;

    AccountUseCase accountUseCase;

    @Override
    @Transactional
    public Long save(MatchMaking matchMaking) {
        List<Long> scheduleIds = matchMaking.schedules()
                .stream()
                .map(m -> m.id().scheduleId())
                .toList();
        List<Schedule> schedules = scheduleUseCase.findAllByScheduleIdIn(scheduleIds);
        LocalDate startAt = schedules
                .stream()
                .map(Schedule::date)
                .min(Comparator.comparing(date -> date))
                .orElse(null);
        LocalDate expiredAt = schedules
                .stream()
                .map(Schedule::date)
                .max(Comparator.comparing(date -> date))
                .orElse(null);
        List<Long> stationResourceIds = matchMaking.resources()
                .stream()
                .map(m -> m.id().stationResourceId())
                .toList();
        Integer totalPrice = stationResourceUseCase.totalPriceByStationResourceIdIn(stationResourceIds)
                * matchMaking.slots().size();
        matchMaking = matchMaking
                .withStartAt(startAt)
                .withExpiredAt(expiredAt)
                .withTotalPrice(totalPrice);
        MatchMaking savedMatchMaking = commandService.save(matchMaking);
        DomainKey<Long> matchMakingId = DomainKey.of(savedMatchMaking.matchMakingId());
        matchScheduleUseCase.save(matchMakingId, matchMaking.schedules());
        slotUseCase.save(matchMakingId, matchMaking.slots());
        resourceUseCase.save(matchMakingId, matchMaking.resources());
        setupSchedule(savedMatchMaking);

        return savedMatchMaking.matchMakingId();
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> matchMakingId, MatchMaking matchMaking) {
        MatchMaking savedMatchMaking = commandService.update(matchMakingId.value(), matchMaking);

        updateSchedule(savedMatchMaking);
    }

    @Override
    @Transactional
    public void process(DomainKey<Long> matchMakingId, Integer paidDeposit, LocalDateTime paidDepositAt) {
        MatchMaking matchMaking = commandService
                .updateStatus(matchMakingId.value(), EMatchMakingStatus.PENDING, paidDepositAt);
        int totalPrice = matchMaking.totalPrice();
        List<MatchParticipant> matchParticipantEmptyList = MatchParticipant
                .ofEmptyList(
                        matchMaking.limitParticipant(),
                        totalPrice,
                        paidDeposit,
                        Long.parseLong(matchMaking.createdBy())
                );
        matchParticipantUseCase.save(DomainKey.of(matchMaking.matchMakingId()), matchParticipantEmptyList);
    }

    @Override
    @Transactional
    public void processParticipant(DomainKey<Long> matchParticipantId, LocalDateTime paidShareAt) {
        matchParticipantUseCase.ready(matchParticipantId, paidShareAt);
    }

    @Override
    @Transactional
    public void prepareToStart(DomainKey<Long> matchMakingId) {
        commandService.updateStatus(matchMakingId.value(), EMatchMakingStatus.PREPARE_START);
    }

    @Override
    @Transactional
    public void start(DomainKey<Long> matchMakingId) {
        List<MatchParticipant> matchParticipants = matchParticipantUseCase
                .findAllByMatchMakingId(matchMakingId);
        if (!matchParticipants.stream()
                .filter(mp -> MyObjectUtils.isEquals(mp.statusCode(), EMatchParticipantStatus.FILLED.getCode()))
                .allMatch(mp -> MyObjectUtils.isEquals(mp.readyStatusCode(),
                EMatchParticipantReadyStatus.READY.getCode()))) {
            throw new MyResourceNotFoundException("Tất cả thành viên phải sẵn sàng");
        }
        MatchMaking matchMaking = commandService
                .updateStatus(matchMakingId.value(), EMatchMakingStatus.STARTED);
        Long ownerWalletId = queryService.findOwnerWalletIdByStationId(matchMaking.stationId())
                .orElseThrow(MyResourceNotFoundException::new);
        paymentUseCase.paymentToStartMatchMaking(matchMaking.withOwnerWalletId(ownerWalletId));
        processStartSchedule(matchMaking);
    }

    @Override
    @Transactional
    public void cancel(DomainKey<Long> matchMakingId) {
        MatchMaking matchMaking = commandService
                .updateStatus(matchMakingId.value(), EMatchMakingStatus.CANCEL);
        Long ownerWalletId = queryService.findOwnerWalletIdByStationId(matchMaking.stationId())
                .orElseThrow(MyResourceNotFoundException::new);

        paymentUseCase.refundMatchMakingDeposit(matchMaking.withOwnerWalletId(ownerWalletId));
    }

    @Override
    @Transactional
    public void finish(DomainKey<Long> matchMakingId) {
        commandService.updateStatus(matchMakingId.value(), EMatchMakingStatus.FINISHED);
    }

    @Override
    @Transactional
    public void handleExpiredJob(DomainKey<Long> matchMakingId) {
        commandService.handleExpiredJob(matchMakingId.value())
                .ifPresent(matchMaking -> {
                    if (MyObjectUtils.isEquals(matchMaking.statusCode(), EMatchMakingStatus.EXPIRED.getCode())) {
                        Long ownerWalletId = queryService.findOwnerWalletIdByStationId(matchMaking.stationId())
                                .orElseThrow(MyResourceNotFoundException::new);
                        paymentUseCase.payDeposit(matchMaking.withOwnerWalletId(ownerWalletId));
                    }
                });
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
                    List<Transaction> transactions = transactionUseCase.findAllByMatchMakingId(DomainKey.of(m.matchMakingId()));
                    Long ownerWalletId = queryService.findOwnerWalletIdByStationId(m.stationId())
                            .orElseThrow(MyResourceNotFoundException::new);
                    Long playerWalletId = queryService.findPlayerWalletIdByCreatedBy(Long.valueOf(m.createdBy()))
                            .orElseThrow(MyResourceNotFoundException::new);
                    List<MatchMakingResource> resources = resourceUseCase
                            .findAllByMatchMakingId(DomainKey.of(m.matchMakingId()));
                    List<MatchMakingSlot> slots = slotUseCase
                            .findAllByMatchMakingId(DomainKey.of(m.matchMakingId()));
                    List<MatchParticipant> participants = matchParticipantUseCase
                            .findAllByMatchMakingId(DomainKey.of(m.matchMakingId()));
                    List<MatchSchedule> schedules = matchScheduleUseCase
                            .findAllByMatchMakingId(DomainKey.of(m.matchMakingId()));

                    return m
                            .withResources(resources)
                            .withSlots(slots)
                            .withOwnerWalletId(ownerWalletId)
                            .withPlayerWalletId(playerWalletId)
                            .withSchedules(schedules)
                            .withParticipants(participants)
                            .withTransactions(transactions);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatchMaking> findAll(MatchMakingCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public Optional<Payment> generateDepositPayment(DomainKey<Long> matchMakingId) {
        return findById(matchMakingId)
                .map(paymentUseCase::createDepositFromMatchMaking);
    }

    @Override
    @Transactional
    public void depositWalletPayment(DomainKey<Long> matchMakingId) {
        MatchMaking matchMaking = findById(matchMakingId).orElseThrow(MyResourceNotFoundException::new);
        paymentUseCase.depositWalletPaymentForMatchMaking(matchMaking);
    }

    @Override
    @Transactional
    public void emptyParticipant(DomainKey<Long> matchParticipantId) {
        MatchParticipantCancel participantCancel = matchParticipantUseCase
                .emptyFilledParticipant(matchParticipantId);

        if (participantCancel.isCancel()) {
            cancel(DomainKey.of(participantCancel.matchMakingId()));
        }
    }

    @Override
    @Transactional
    public Payment participantPayment(DomainKey<Long> matchParticipantId, EPaymentMethod paymentMethod) {
        if (!queryService.allowReadyByMatchParticipantId(matchParticipantId.value())) {
            throw new MyResourceNotValid("Chưa thể sẵn sàng ngay lúc này");
        }
        MatchParticipant updateMatchParticipant = matchParticipantUseCase
                .updatePaymentMethod(matchParticipantId, paymentMethod);

        return switch(paymentMethod) {
            case WALLET -> {
                paymentUseCase.walletPaymentForMatchParticipant(updateMatchParticipant);
                yield null;
            }
            case BANK_TRANSFER -> paymentUseCase.createFromMatchParticipant(updateMatchParticipant);
            case DIRECT -> {
                paymentUseCase.directPaymentForMatchParticipant(updateMatchParticipant);
                yield null;
            }
        };
    }

    @Override
    @Transactional
    public void prepareStart(DomainKey<Long> matchMakingId) {
        commandService.updateStatus(matchMakingId.value(), EMatchMakingStatus.PREPARE_START);
    }

    private void setupSchedule(MatchMaking matchMaking) {
        try {
            JobDetail endMatchMakingDetail = JobBuilder.newJob(ExpiredMatchMakingJob.class)
                    .withIdentity("endMatchMakingJobDetail_%s".formatted(matchMaking.matchMakingId()), "matchMaking")
                    .usingJobData("matchMakingId", matchMaking.matchMakingId())
                    .build();
            Trigger endMatchMakingTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("endMatchMakingTrigger_%s".formatted(matchMaking.matchMakingId()), "matchMaking")
                    .startAt(Timestamp.valueOf(matchMaking.expiredAt().atStartOfDay()))
                    .build();
            Map<JobDetail, Set<? extends Trigger>> jobAndTrigger = new LinkedHashMap<>();
            jobAndTrigger.put(endMatchMakingDetail, Set.of(endMatchMakingTrigger));
            scheduler.scheduleJobs(jobAndTrigger, true);
        } catch (Exception e) {
            log.error("[MatchMakingUseCaseService.setupSchedule(...)] message: {}", e.getMessage(), e);
        }

    }

    private void updateSchedule(MatchMaking matchMaking) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey("endMatchMakingTrigger_%s".formatted(matchMaking.matchMakingId()),
                    "matchMaking");
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .startAt(Timestamp.valueOf(matchMaking.expiredAt().atStartOfDay()))
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            log.error("[MatchMakingUseCaseService.updateSchedule(...)] message: {}", e.getMessage(), e);
        }
    }

    private void processStartSchedule(MatchMaking matchMaking) {
        try {
            Long matchMakingId = matchMaking.matchMakingId();
            List<MatchMakingSlot> matchMakingSlots = slotUseCase
                    .findAllByMatchMakingId(DomainKey.of(matchMaking.matchMakingId()));
            LocalDateTime endAt = matchMakingSlots
                    .stream()
                    .max(Comparator.comparing(MatchMakingSlot::end))
                    .map(m -> matchMaking.playAt().toLocalDate().atTime(m.end()))
                    .orElseThrow(MyResourceNotFoundException::new);
            TriggerKey triggerKey = TriggerKey.triggerKey("endMatchMakingTrigger_%s".formatted(matchMakingId), "matchMaking");
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .startAt(Timestamp.valueOf(endAt))
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            log.error("[MatchMakingUseCaseService.processSchedule(...)] message: {}", e.getMessage(), e);
        }
    }
}
