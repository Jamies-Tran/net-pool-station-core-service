package net.pool.station.core.features.timeslot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
        Map<Long, List<BookingSlot>> bookingSlots = bookingSlotUseCase
                .findAllByStationResourceIdAndTimeSlotIdIn(stationResourceId, timeSlotIds)
                .stream()
                .collect(Collectors.groupingBy(b -> b.bookingSlotId().timeSlotId()));
        Map<Long, List<MatchMakingSlot>> matchMakingSlots = matchMakingSlotUseCase
                .findAllByStationResourceIdAndTimeSlotIdIn(stationResourceId, timeSlotIds)
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

                    List<MatchMakingSlot> matchMakingSlotList = matchMakingSlots.computeIfAbsent(t.timeSlotId(), k -> List.of());

                    if ((t.date().isEqual(now.toLocalDate())
                            && (t.begin().isAfter(now.toLocalTime()) && t.end().isAfter(now.toLocalTime())))
                            || t.date().isAfter(now.toLocalDate()) ) {
                        dateCheck = true;
                    }

                    if (MyObjectUtils.isNotEmpty(bookingSlotList)) {
                        bookingCheck = bookingSlotList
                                .stream()
                                .noneMatch(b -> b.date().isEqual(now.toLocalDate())
                                        && b.begin().isBefore(now.toLocalTime()) && b.end().isAfter(now.toLocalTime()));
                    }

                    if (MyObjectUtils.isNotEmpty(matchMakingSlotList)) {
                        matchMakingCheck = matchMakingSlotList
                                .stream()
                                .noneMatch(m -> m.startAt().isBefore(now.toLocalDate())
                                        && m.expiredAt().isAfter(now.toLocalDate())
                                        && m.begin().isBefore(now.toLocalTime()) && m.end().isAfter(now.toLocalTime()));
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
