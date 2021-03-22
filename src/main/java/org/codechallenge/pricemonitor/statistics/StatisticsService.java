package org.codechallenge.pricemonitor.statistics;

import org.codechallenge.pricemonitor.ticks.ValidatedTicks;

/**
 * This interface is for the Statistics Methods Abstraction.
 * 
 * @author Mohsin Khan
 */


public interface StatisticsService {

	final long slidingIntervalInSeconds = 60;

	void updateStatistics(ValidatedTicks validatedTicks);

	Statistics getStatistics();

	Statistics getStatistics(String instrument);

	void customGarbageCollector();

}
