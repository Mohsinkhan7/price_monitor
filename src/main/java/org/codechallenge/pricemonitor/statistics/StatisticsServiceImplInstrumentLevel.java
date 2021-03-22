package org.codechallenge.pricemonitor.statistics;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.codechallenge.pricemonitor.ticks.ValidatedTicks;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is to implement logic for instrument-wise statistics.
 * 
 * @author Mohsin Khan
 */

@Slf4j
@Service
public class StatisticsServiceImplInstrumentLevel extends StatisticsServiceImpl {

	//Main Data Structure for Instrument wise Statistics Calculations
	ConcurrentHashMap<Long, ConcurrentHashMap<String, Statistics>> statisticsMap = new ConcurrentHashMap<>();

	public Statistics getStatistics(String instrument) {
		//Converting UNIX time from milliseconds into Seconds
		final long currentSeconds = System.currentTimeMillis()/1000;

		if(statisticsMap.containsKey(currentSeconds))
		{
			ConcurrentHashMap<String, Statistics> instrumentStatisticsMap=statisticsMap.get(currentSeconds);
			if(instrumentStatisticsMap.containsKey(instrument))
			{
				return instrumentStatisticsMap.get(instrument);
			}
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
			if(statisticsMap.containsKey(timeStampInSeconds+i))
			{
				ConcurrentHashMap<String, Statistics> instrumentStatisticsMap = statisticsMap.get(timeStampInSeconds+i);
				if(instrumentStatisticsMap.containsKey(validatedTicks.getInstrument()))
				{

					if(instrumentStatisticsMap.containsKey(validatedTicks.getInstrument()))
					{
						instrumentStatisticsMap.put(validatedTicks.getInstrument(),calculateStatistics(instrumentStatisticsMap.get(validatedTicks.getInstrument()),validatedTicks));
						statisticsMap.put(timeStampInSeconds+i,instrumentStatisticsMap);

					}
					else
					{
						instrumentStatisticsMap.put(validatedTicks.getInstrument(),new Statistics(validatedTicks.getPrice()));
						statisticsMap.put(timeStampInSeconds+i,instrumentStatisticsMap);

					}
				}
				else
				{
					instrumentStatisticsMap.put(validatedTicks.getInstrument(),new Statistics(validatedTicks.getPrice()));
					statisticsMap.put(timeStampInSeconds+i,instrumentStatisticsMap);

				}
			}
			else
			{
				ConcurrentHashMap<String, Statistics> instrumentStatisticsMap = new ConcurrentHashMap();
				instrumentStatisticsMap.put(validatedTicks.getInstrument(),new Statistics(validatedTicks.getPrice()));
				statisticsMap.put(timeStampInSeconds+i,instrumentStatisticsMap);
			}
		}

	}

	@Override
	public void customGarbageCollector() {

		final long currentSeconds = System.currentTimeMillis()/1000;

		log.info("Instrument Garbage Collector executed at {} unix seconds",currentSeconds);

		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			for(int i=1;i<=slidingIntervalInSeconds;i++)
			{
				statisticsMap.remove(currentSeconds-i);
			};
		});
	}

	@Override
	public Statistics getStatistics() {

		return null;
	}

}
