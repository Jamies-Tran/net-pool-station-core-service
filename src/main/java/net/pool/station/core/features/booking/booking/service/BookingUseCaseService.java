package net.pool.station.core.features.booking.booking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EBookingStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.booking.BookingCriteria;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.domain.booking.menu.BookingMenu;
import net.pool.station.core.domain.booking.menu.BookingMenuUseCase;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import net.pool.station.core.domain.booking.slot.BookingSlotUseCase;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.payment.Payment;
import net.pool.station.core.domain.payment.PaymentUseCase;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.schedule.ScheduleUseCase;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.domain.station.resource.StationResourceUseCase;
import net.pool.station.core.features.booking.job.ExpiredBookingJob;
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

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional
    public void save(Booking booking) {
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        Booking savedBooking = commandService.save(booking.withAccountId(loginInfo.accountId()));
        DomainKey<Long> bookingId = DomainKey.of(savedBooking.bookingId());
        bookingMenuUseCase.save(bookingId, booking.bookingMenus());
        bookingSlotUseCase.save(bookingId, booking.bookingSlots());
        if (MyObjectUtils.isEquals(EBookingStatus.NEW.getCode(), savedBooking.statusCode())) {
            scheduleBooking(savedBooking);
        }
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

                    return booking
                            .withWalletId(stationOwnerWalletId)
                            .withSchedule(schedule)
                            .withStationResource(stationResource)
                            .withBookingMenus(bookingMenus)
                            .withBookingSlots(bookingSlots);
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
    public void update(DomainKey<Long> bookingId, Booking booking) {
        commandService.update(bookingId.value(), booking);
        updateScheduleBooking(booking);
    }

    @Override
    @Transactional
    public void processed(DomainKey<Long> bookingId) {
        Booking booking = commandService.updateStatus(bookingId.value(), EBookingStatus.NEW);
        scheduleBooking(booking);
    }

    @Override
    @Transactional
    public void start(DomainKey<Long> bookingId) {
        commandService.updateStatus(bookingId.value(), EBookingStatus.PROCESSING);
    }

    @Override
    @Transactional
    public Booking finish(DomainKey<Long> bookingId) {
        Booking booking = commandService.updateStatus(bookingId.value(), EBookingStatus.COMPLETED);
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

        return booking
                .withWalletId(stationOwnerWalletId)
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
            JobDetail jobDetail = JobBuilder.newJob(ExpiredBookingJob.class)
                    .withIdentity("expiredBookingJob_%s".formatted(booking.bookingId()), "booking")
                    .usingJobData("bookingId", booking.bookingId())
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("expiredBookingTrigger_%s".formatted(booking.bookingId()), "booking")
                    .startAt(Timestamp.valueOf((booking.endAt())))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new MyResourceNotValid("Vui lòng thử lại sau");
        }
    }

    private void updateScheduleBooking(Booking booking) {
        try {
            TriggerKey triggerKey = new TriggerKey("expiredBookingJob_%s".formatted(booking.bookingId()),
                    "booking");
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .startAt(Timestamp.valueOf((booking.endAt())))
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);
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
