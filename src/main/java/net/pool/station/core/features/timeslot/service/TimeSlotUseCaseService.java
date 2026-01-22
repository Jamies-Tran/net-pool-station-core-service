package net.pool.station.core.features.timeslot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EBookingStatus;
import net.pool.station.core.bootstrap.enums.EMatchMakingStatus;
import net.pool.station.core.bootstrap.enums.ETimeSlotStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import net.pool.station.core.domain.booking.slot.BookingSlotUseCase;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlotUseCase;
import net.pool.station.core.domain.timeslot.TimeSlot;
import net.pool.station.core.domain.timeslot.TimeSlotCriteria;
import net.pool.station.core.domain.timeslot.TimeSlotUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeSlotUseCaseService implements TimeSlotUseCase {
    TimeSlotCommandService commandService;

    TimeSlotQueryService queryService;

    BookingSlotUseCase bookingSlotUseCase;

    MatchMakingSlotUseCase matchMakingSlotUseCase;

    @Override
    @Transactional
    @Async("timeSlotExecutor")
    public void save(List<TimeSlot> timeSlots) {
        commandService.save(timeSlots);
    }

    @Override
    @Transactional
    public void save(DomainKey<Long> scheduleId, List<TimeSlot> timeSlots) {
        commandService.save(scheduleId.value(), timeSlots);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TimeSlot> findById(DomainKey<Long> timeSlotId) {
        return queryService.findById(timeSlotId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlot> findAllByIdIn(List<Long> timeSlotIds) {
        return queryService.findAllByTimeSlotIdIn(timeSlotIds);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TimeSlot> findAll(TimeSlotCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlot> findAllByScheduleId(DomainKey<Long> scheduleId) {
        return queryService.findAllByScheduleId(scheduleId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlot> findAllByScheduleIdAndStationResourceId(DomainKey<Long> scheduleId, DomainKey<Long> stationResourceId) {
        List<TimeSlot> timeSlots = queryService.findAllByScheduleId(scheduleId.value());
        Map<Long, Boolean> allowBooking = allowBooking(stationResourceId, timeSlots);

        return timeSlots
                .stream()
                .map(t -> t.withAllowBooking(allowBooking.computeIfAbsent(t.timeSlotId(), k -> true)))
                .toList();
    }

    private Map<Long, Boolean> allowBooking(DomainKey<Long> stationResourceId, List<TimeSlot> timeSlots) {
        List<Long> timeSlotIds = timeSlots
                .stream()
                .map(TimeSlot::timeSlotId)
                .toList();
        List<String> bookingStatusCodes = Stream.of(EBookingStatus.values())
                .map(EBookingStatus::getCode)
                .filter(s -> MyObjectUtils.isEquals(s, EBookingStatus.PENDING.getCode())
                        || MyObjectUtils.isEquals(s, EBookingStatus.NEW.getCode())
                        || MyObjectUtils.isEquals(s, EBookingStatus.PROCESSING.getCode()))
                .toList();
        List<String> matchMakingStatusCodes = Stream.of(EMatchMakingStatus.values())
                .map(EMatchMakingStatus::getCode)
                .filter(m -> MyObjectUtils.isEquals(m, EMatchMakingStatus.DRAFT.getCode())
                        || MyObjectUtils.isEquals(m, EMatchMakingStatus.PENDING.getCode())
                        || MyObjectUtils.isEquals(m, EMatchMakingStatus.PREPARE_START.getCode())
                        || MyObjectUtils.isEquals(m, EMatchMakingStatus.STARTED.getCode()))
                .toList();
        Map<Long, List<BookingSlot>> bookingSlots = bookingSlotUseCase
                .findAllByStationResourceIdAndTimeSlotIdInAndBookingStatusCodeIn(stationResourceId, timeSlotIds, bookingStatusCodes)
                .stream()
                .collect(Collectors.groupingBy(b -> b.bookingSlotId().timeSlotId()));
        Map<Long, List<MatchMakingSlot>> matchMakingSlots = matchMakingSlotUseCase
                .findAllByStationResourceIdAndStatusCodeIn(stationResourceId, matchMakingStatusCodes)
                .stream()
                .collect(Collectors.groupingBy(m -> m.id().timeSlotId()));

        return timeSlots
                .stream()
                .collect(Collectors.toMap(TimeSlot::timeSlotId, t -> {
                    LocalDateTime now = LocalDateTime.now();
                    boolean dateCheck = false;
                    boolean bookingCheck = true;
                    boolean matchMakingCheck = true;

                    List<BookingSlot> bookingSlotList = bookingSlots.computeIfAbsent(t.timeSlotId(), k -> List.of());

                    if ((t.date().isEqual(now.toLocalDate())
                            && (t.begin().isAfter(now.toLocalTime()) && t.end().isAfter(now.toLocalTime())))
                            || t.date().isAfter(now.toLocalDate()) ) {
                        dateCheck = true;
                    }

                    if (MyObjectUtils.isNotEmpty(bookingSlotList)) {
                        bookingCheck = bookingSlotList
                                .stream()
                                .noneMatch(b -> b.date().isEqual(t.date())
                                        && b.begin().equals(t.begin()) && b.end().equals(t.end()));
                    }

                    if (MyObjectUtils.isNotEmpty(matchMakingSlots)) {
                        matchMakingCheck = matchMakingSlots.entrySet()
                                .stream()
                                .noneMatch(entry -> entry.getValue().stream()
                                        .anyMatch(m -> {
                                            if (MyObjectUtils.isEquals(m.matchMakingStatusCode(),
                                                    EMatchMakingStatus.STARTED.getCode())) {
                                                return m.playAt().toLocalDate().equals(t.date())
                                                        && (m.playAt().toLocalTime().isAfter(t.begin()) || m.playAt().toLocalTime().equals(t.begin()))
                                                        && m.playAt().toLocalTime().isBefore(t.end());
                                            }
                                            return (m.startAt().isBefore(t.date()) || m.startAt().equals(t.date()))
                                                    && m.expiredAt().isAfter(t.date())
                                                    && m.begin().equals(t.begin()) && m.end().equals(t.end());
                                        }));
                    }

                    return dateCheck && bookingCheck && matchMakingCheck;
                }));

    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> timeSlotId) {
        commandService.delete(timeSlotId.value());
    }


}
