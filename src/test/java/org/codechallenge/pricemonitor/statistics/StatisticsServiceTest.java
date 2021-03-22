package org.codechallenge.pricemonitor.statistics;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.concurrent.CompletableFuture;

import org.codechallenge.pricemonitor.ticks.ValidatedTicks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * Unit Testing for Statistics Service
 *
 * @author Mohsin Khan
 * 
 */


public class StatisticsServiceTest {

	private StatisticsService statisticsService;

	@BeforeEach
	void Initialize() 
	{
		statisticsService=new StatisticsServiceImplAggregated();
	}

	@Test
	void getSatisticsTest() throws Exception
	{
		//GIVEN
		for(int i=1;i<=10;i++)
		{
			statisticsService.updateStatistics(new ValidatedTicks("ABC", i, System.currentTimeMillis()));
		}

		//WHEN
		Statistics result = statisticsService.getStatistics();

		//THEN
		then(result.getAvg()).isEqualTo(5.5);
		then(result.getMax()).isEqualTo(10.0);
		then(result.getMin()).isEqualTo(1.0);
		then(result.getCount()).isEqualTo(10L);
	}

	@Test
	void updateStatisticsFutureTest()throws Exception
	{
		//GIVEN
		long tmS = System.currentTimeMillis();


		for(double i=4;i<100;i++)
		{
			statisticsService.updateStatistics(new ValidatedTicks("ABC", i, tmS));
		}





		for(double i=100;i<=200;i++)
		{
			statisticsService.updateStatistics(new ValidatedTicks("ABC", i, tmS));
		}





		//WHEN
		Statistics result = statisticsService.getStatistics();

		//THEN
		then(result.getAvg()).isEqualTo(102.0);
		then(result.getMax()).isEqualTo(200.0);
		then(result.getMin()).isEqualTo(4.0);
		then(result.getCount()).isEqualTo(197L); //196




	}

	@Test
	void sldingIntervalStatisticsFutureTest() throws Exception
	{
		//GIVEN
		long tmS = System.currentTimeMillis();

		for(int i=1;i<31;i++)
		{
			statisticsService.updateStatistics(new ValidatedTicks("ABC", i,tmS -(i*1000)));
		}



		for(int i=60;i<91;i++)
		{
			statisticsService.updateStatistics(new ValidatedTicks("ABC", i, tmS-(i*1000)));
		}




		//WHEN
		Statistics result = statisticsService.getStatistics();

		//THEN
		then(result.getAvg()).isEqualTo(15.5);
		then(result.getMax()).isEqualTo(30.0);
		then(result.getMin()).isEqualTo(1.0);
		then(result.getCount()).isEqualTo(30L);

	}

	@Test
	void updateInstrumentStatisticsTest()throws Exception
	{
		//GIVEN
		StatisticsService statisticsInstrumentService=new StatisticsServiceImplInstrumentLevel();
		long tmS = System.currentTimeMillis();


		for(double i=1;i<=100;i++)
		{
			ValidatedTicks vtemp = new ValidatedTicks("ABC", i, tmS );
			statisticsService.updateStatistics(vtemp);
			statisticsInstrumentService.updateStatistics(vtemp);
		}





		for(double i=101;i<=200;i++)
		{
			ValidatedTicks vtemp = new ValidatedTicks("DEF", i, tmS );
			statisticsService.updateStatistics(vtemp);
			statisticsInstrumentService.updateStatistics(vtemp);
		}


		//WHEN
		Statistics result = statisticsService.getStatistics();

		//THEN
		then(result.getAvg()).isEqualTo(100.5);
		then(result.getMax()).isEqualTo(200.0);
		then(result.getMin()).isEqualTo(1.0);
		then(result.getCount()).isEqualTo(200L);

		result = statisticsInstrumentService.getStatistics("FHG");

		//THEN
		then(result.getAvg()).isEqualTo(0.0);
		then(result.getMax()).isEqualTo(0.0);
		then(result.getMin()).isEqualTo(0.0);
		then(result.getCount()).isEqualTo(0L);

		result = statisticsInstrumentService.getStatistics("ABC");

		//THEN
		then(result.getAvg()).isEqualTo(50.5);
		then(result.getMax()).isEqualTo(100.0);
		then(result.getMin()).isEqualTo(1.0);
		then(result.getCount()).isEqualTo(100L);

		result = statisticsInstrumentService.getStatistics("DEF");

		//THEN
		then(result.getAvg()).isEqualTo(150.5);
		then(result.getMax()).isEqualTo(200.0);
		then(result.getMin()).isEqualTo(101.0);
		then(result.getCount()).isEqualTo(100L);
	}

}
