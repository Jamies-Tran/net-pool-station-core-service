package net.pool.station.core.features.booking.booking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EBookingStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.booking.BookingCriteria;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.domain.booking.menu.BookingMenu;
import net.pool.station.core.domain.booking.menu.BookingMenuUseCase;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import net.pool.station.core.domain.booking.slot.BookingSlotUseCase;
import net.pool.station.core.domain.fcm.info.FcmInfo;
import net.pool.station.core.domain.fcm.info.FcmInfoUseCase;
import net.pool.station.core.domain.notification.NotificationUseCase;
import net.pool.station.core.domain.notification.Notification;
import net.pool.station.core.domain.payment.Payment;
import net.pool.station.core.domain.payment.PaymentUseCase;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.schedule.ScheduleUseCase;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.domain.station.resource.StationResourceUseCase;
import net.pool.station.core.features.booking.job.ExpiredBookingJob;
import net.pool.station.core.features.booking.job.StartBookingJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingUseCaseService implements BookingUseCase {
    BookingCommandService commandService;

    BookingQueryService queryService;

    StationResourceUseCase stationResourceUseCase;

    BookingMenuUseCase bookingMenuUseCase;

    BookingSlotUseCase bookingSlotUseCase;

    ScheduleUseCase scheduleUseCase;

    Scheduler scheduler;

    PaymentUseCase paymentUseCase;

    NotificationUseCase notificationUseCase;

    FcmInfoUseCase fcmInfoUseCase;

    AccountUseCase accountUseCase;

    @Override
    @Transactional
    public void save(Booking booking) {
        Booking savedBooking = commandService.save(booking);
        DomainKey<Long> bookingId = DomainKey.of(savedBooking.bookingId());
        if (MyObjectUtils.isNotEmpty(booking.bookingMenus())) {
            bookingMenuUseCase.save(bookingId, booking.bookingMenus());
        }
        bookingSlotUseCase.save(bookingId, booking.bookingSlots());
        if (MyObjectUtils.isEquals(EBookingStatus.NEW.getCode(), savedBooking.statusCode())) {
            scheduleBooking(savedBooking);
            notificationUseCase.pushNotification(Notification.ofBooking(fcmInfos(savedBooking), savedBooking));
        }
    }

    private List<FcmInfo> fcmInfos(Booking booking) {
        List<Account> stationAdmins = accountUseCase
                .findAllStationAdminByStationResourceId(new DomainKey<>(booking.stationResourceId()));

        return fcmInfoUseCase.findAllByAccountIdIn(stationAdmins.stream().map(Account::accountId).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findById(DomainKey<Long> bookingId) {
        return queryService.findById(bookingId.value())
                .map(booking -> {
                    Long stationOwnerWalletId = queryService.findStationOwnerWalletId(booking.stationResourceId())
                            .orElse(null);
                    Schedule schedule = scheduleUseCase.findById(DomainKey.of(booking.scheduleId()))
                            .orElse(null);
                    StationResource stationResource = stationResourceUseCase
                            .findById(DomainKey.of(booking.stationResourceId()))
                            .orElse(null);
                    List<BookingMenu> bookingMenus = bookingMenuUseCase
                            .findAllByBookingId(DomainKey.of(booking.bookingId()));
                    List<BookingSlot> bookingSlots = bookingSlotUseCase
                            .findAllByBookingId(DomainKey.of(booking.bookingId()));
                    Account account = accountUseCase.findById(DomainKey.of(Long.valueOf(booking.createdBy())))
                            .orElse(null);

                    return booking
                            .withOwnerWalletId(stationOwnerWalletId)
                            .withSchedule(schedule)
                            .withStationResource(stationResource)
                            .withBookingMenus(bookingMenus)
                            .withBookingSlots(bookingSlots)
                            .withAccount(account);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Booking> findAll(BookingCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public Optional<Payment> generatePayment(DomainKey<Long> bookingId) {
        Optional<Booking> booking = findById(bookingId);
        return booking.map(paymentUseCase::createFromBooking);
    }

    @Override
    @Transactional
    public void walletPayment(DomainKey<Long> bookingId) {
        Booking booking = findById(bookingId).orElseThrow(MyResourceNotFoundException::new);
        paymentUseCase.walletPaymentForBooking(booking);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> bookingId, Booking booking) {
        Booking updateBooking = commandService.update(bookingId.value(), booking);
        bookingMenuUseCase.update(DomainKey.of(bookingId.value()), booking.bookingMenus());
        bookingSlotUseCase.update(DomainKey.of(bookingId.value()), booking.bookingSlots());

        updateScheduleBooking(updateBooking);
    }

    @Override
    @Transactional
    public void processed(DomainKey<Long> bookingId) {
        Booking booking = commandService.updateStatus(bookingId.value(), EBookingStatus.NEW);
        scheduleBooking(booking);
        notificationUseCase.pushNotification(Notification.ofBooking(fcmInfos(booking), booking));
    }

    @Override
    @Transactional
    public void start(DomainKey<Long> bookingId) {
        commandService.updateStatus(bookingId.value(), EBookingStatus.PROCESSING);
    }

    @Override
    @Transactional
    public void autoStart(DomainKey<Long> bookingId) {
        Booking booking = queryService.findById(bookingId.value())
                .orElseThrow(MyResourceNotFoundException::new);
        if (MyObjectUtils.isEquals(EBookingStatus.PENDING.getCode(), booking.statusCode())) {
            commandService.updateStatus(booking.bookingId(), "Người dùng chưa thanh toán",
                    EBookingStatus.CANCELED);
        } else {
            commandService.updateStatus(booking.bookingId(), EBookingStatus.PROCESSING);
        }
    }

    @Override
    @Transactional
    public Booking finish(DomainKey<Long> bookingId) {
        Booking booking = queryService.findById(bookingId.value())
                .orElseThrow(MyResourceNotFoundException::new);
        if (MyObjectUtils.isEquals(EBookingStatus.CANCELED.getCode(), booking.statusCode())) {
            return booking;
        }
        Booking completedBooking = commandService.updateStatus(booking.bookingId(), EBookingStatus.COMPLETED);
        Long stationOwnerWalletId = queryService.findStationOwnerWalletId(booking.stationResourceId())
                .orElse(null);
        Schedule schedule = scheduleUseCase.findById(DomainKey.of(booking.scheduleId()))
                .orElse(null);
        StationResource stationResource = stationResourceUseCase
                .findById(DomainKey.of(booking.stationResourceId()))
                .orElse(null);
        List<BookingMenu> bookingMenus = bookingMenuUseCase
                .findAllByBookingId(DomainKey.of(booking.bookingId()));
        List<BookingSlot> bookingSlots = bookingSlotUseCase
                .findAllByBookingId(DomainKey.of(booking.bookingId()));
        Integer resourcePrice = Optional.ofNullable(stationResource).map(StationResource::price)
                .orElse(0) * bookingSlots.size();
        Integer menuPrice = bookingMenus.stream()
                .map(BookingMenu::price)
                .reduce(0, Integer::sum);
        int totalPrice = resourcePrice + menuPrice;
        return completedBooking
                .withTotalPrice(totalPrice)
                .withOwnerWalletId(stationOwnerWalletId)
                .withSchedule(schedule)
                .withStationResource(stationResource)
                .withBookingMenus(bookingMenus)
                .withBookingSlots(bookingSlots);
    }

    @Override
    @Transactional
    public void cancel(DomainKey<Long> bookingId, String cancelReason) {
        commandService.updateStatus(bookingId.value(), cancelReason, EBookingStatus.CANCELED);
        deleteScheduleBooking(bookingId.value());
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> bookingId) {
        commandService.delete(bookingId.value());
    }

    private void scheduleBooking(Booking booking) {
        try {
            JobDetail startBookingJob = JobBuilder.newJob(StartBookingJob.class)
                    .withIdentity("startBookingJob_%s".formatted(booking.bookingId()), "booking")
                    .usingJobData("bookingId", booking.bookingId())
                    .build();
            JobDetail expiredBookingJob = JobBuilder.newJob(ExpiredBookingJob.class)
                    .withIdentity("expiredBookingJob_%s".formatted(booking.bookingId()), "booking")
                    .usingJobData("bookingId", booking.bookingId())
                    .build();
            Trigger startBookingTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("startBookingTrigger_%s".formatted(booking.bookingId()), "booking")
                    .startAt(Timestamp.valueOf(booking.startAt()))
                    .build();
            Trigger expiredBookingTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("expiredBookingTrigger_%s".formatted(booking.bookingId()), "booking")
                    .startAt(Timestamp.valueOf((booking.endAt())))
                    .build();
            Map<JobDetail, Set<? extends Trigger>> jobAndTriggers = new HashMap<>();
            jobAndTriggers.put(startBookingJob, Set.of(startBookingTrigger));
            jobAndTriggers.put(expiredBookingJob, Set.of(expiredBookingTrigger));
            scheduler.scheduleJobs(jobAndTriggers, true);
        } catch (SchedulerException e) {
            throw new MyResourceNotValid("Vui lòng thử lại sau");
        }
    }

    private void updateScheduleBooking(Booking booking) {
        try {
            TriggerKey startTriggerKey = new TriggerKey("startBookingTrigger_%s".formatted(booking.bookingId()),
                    "booking");
            TriggerKey expiredTriggerKey = new TriggerKey("expiredBookingJob_%s".formatted(booking.bookingId()),
                    "booking");
            Trigger startTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(startTriggerKey)
                    .startAt(Timestamp.valueOf(booking.startAt()))
                    .build();
            Trigger expiredTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(expiredTriggerKey)
                    .startAt(Timestamp.valueOf((booking.endAt())))
                    .build();
            scheduler.rescheduleJob(startTriggerKey, startTrigger);
            scheduler.rescheduleJob(expiredTriggerKey, expiredTrigger);
        } catch (SchedulerException e) {
            throw new MyResourceNotValid("Vui lòng thử lại sau");
        }
    }

    private void deleteScheduleBooking(Long bookingId) {
        try {
            JobKey jobKey = new JobKey("expiredBookingJob_%s".formatted(bookingId),
                    "booking");
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new MyResourceNotValid("Vui lòng thử lại sau");
        }
    }
}
