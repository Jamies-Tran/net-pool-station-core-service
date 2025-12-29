package net.pool.station.core.features.match.making.job;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.making.MatchMakingUseCase;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpiredMatchMakingJob implements Job {
    MatchMakingUseCase matchMakingUseCase;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Long matchMakingId = jobExecutionContext.getJobDetail().getJobDataMap()
                .getLong("matchMakingId");

        matchMakingUseCase.handleExpiredJob(DomainKey.of(matchMakingId));
    }
}
