package org.codechallenge.pricemonitor.statistics;

import org.codechallenge.pricemonitor.ticks.ValidatedTicks;

/**
 * This abstract class implements common methods for Aggregated and Instrument Level Statistics.
 * Also, contains Running Average Logic for O(1) Algo
 * 
 * @author Mohsin Khan
 */

public abstract class StatisticsServiceImpl implements StatisticsService {

	protected Statistics calculateStatistics(Statistics updatedStats,ValidatedTicks validatedTicks)
	{
		double instrumentPrice = validatedTicks.getPrice();

		//Average is calculated with the formula of Running Average
		updatedStats.setAvg( (updatedStats.getAvg()*updatedStats.getCount()+instrumentPrice)/(updatedStats.getCount()+1));

		//Minimum Calculations
		if(updatedStats.getMin() > instrumentPrice) updatedStats.setMin (instrumentPrice);

		//Maximum Calculations
		if(updatedStats.getMax() < instrumentPrice) updatedStats.setMax (instrumentPrice);

		//Counter Increment
		updatedStats.setCount(updatedStats.getCount()+1);

		return updatedStats;
	}

}
