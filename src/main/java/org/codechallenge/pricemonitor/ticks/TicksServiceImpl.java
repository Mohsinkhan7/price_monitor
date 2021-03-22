package org.codechallenge.pricemonitor.ticks;

import javax.validation.Valid;

import org.codechallenge.pricemonitor.statistics.StatisticsService;
import org.codechallenge.pricemonitor.statistics.StatisticsServiceImplAggregated;
import org.codechallenge.pricemonitor.statistics.StatisticsServiceImplInstrumentLevel;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Implementation class to implement business logic.
 *
 * @author Mohsin Khan
 * 
 */
@Service
public class TicksServiceImpl implements TicksService {

	public static final StatisticsService statisticsService= new StatisticsServiceImplAggregated();
	public static final StatisticsService statisticsInstrumentService= new StatisticsServiceImplInstrumentLevel();

	@Override
	public HttpStatus recieveInupt(@Valid ValidatedTicks validatedTicks) {

		//Converting UNIX time from milliseconds into Seconds
		final long currentSeconds = System.currentTimeMillis()/1000;

		if(currentSeconds-(validatedTicks.getTimestamp()/1000)>=StatisticsService.slidingIntervalInSeconds)
		{
			return HttpStatus.valueOf(204);
		}
		else
		{
			statisticsService.updateStatistics(validatedTicks);
			statisticsInstrumentService.updateStatistics(validatedTicks);
			return HttpStatus.valueOf(201);
		}

	}

	@Scheduled(fixedRate = 10000)
	public void invokeCustomGarbageCollector() 
	{
		statisticsService.customGarbageCollector();
		statisticsInstrumentService.customGarbageCollector();
	}

}
