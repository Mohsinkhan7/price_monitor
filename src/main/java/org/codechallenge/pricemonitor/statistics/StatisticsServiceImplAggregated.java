package org.codechallenge.pricemonitor.statistics;

import org.codechallenge.pricemonitor.ticks.ValidatedTicks;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;
/**
 * This class is to Implement logic for Aggregated Statistics.
 * 
 * @author Mohsin Khan
 */

@Slf4j
@Service
public class StatisticsServiceImplAggregated extends StatisticsServiceImpl {

	ConcurrentHashMap<Long, Statistics> statisticsMap = new ConcurrentHashMap<>();

	@Override
	public Statistics getStatistics() {

		//Converting UNIX time from milliseconds into Seconds
		final long currentSeconds = System.currentTimeMillis()/1000;

		if(statisticsMap.containsKey(currentSeconds))
		{
			return statisticsMap.get(currentSeconds);
		}

		return new Statistics();
	}

	@Override
	public void updateStatistics(ValidatedTicks validatedTicks) {

		long timeStampInSeconds = validatedTicks.getTimestamp()/1000;

		//log.info("Time in Second: {}, Instrument: {}, Price: {}",timeStampInSeconds,validatedTicks.getInstrument(),validatedTicks.getPrice());

		//calculate the Average for future sliding interval seconds in advance utilizing thread pool
		for(int i=0;i<slidingIntervalInSeconds;i++)
		{
			Statistics currentStats = statisticsMap.get(timeStampInSeconds+i);

			if(currentStats==null)
			{
				statisticsMap.put(timeStampInSeconds+i,new Statistics(validatedTicks.getPrice()));
			}
			else
			{
				//Put statement is explicitly added for perfect Hash Map segment lock
				statisticsMap.put(timeStampInSeconds+i,calculateStatistics(currentStats,validatedTicks));
			}

		}
	}

	@Override
	public void customGarbageCollector() {

		final long currentSeconds = System.currentTimeMillis()/1000;

		log.info("Aggregated Garbage Collector executed at {} unix seconds",currentSeconds);

		CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
			for(int i=1;i<=slidingIntervalInSeconds;i++)
			{
				statisticsMap.remove(currentSeconds-i);
			};
		});
	}


	@Override
	public Statistics getStatistics(String instrument) {

		return null;
	}

}
