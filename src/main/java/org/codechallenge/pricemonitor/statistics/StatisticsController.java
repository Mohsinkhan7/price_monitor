package org.codechallenge.pricemonitor.statistics;

import org.codechallenge.pricemonitor.ticks.TicksServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This class implements the REST API for statistics and instrument wise statistics
 * 
 * @author Mohsin Khan
 */

@Slf4j
@RequiredArgsConstructor
@RestController
//@RequestMapping("/statistics")
public class StatisticsController {

	@GetMapping("/statistics")
	Statistics getAggregatedStatistics() {


		Statistics statistics = TicksServiceImpl.statisticsService.getStatistics();

		return statistics;
	}

	@GetMapping("/statistics/{instrument_identifier}")
	Statistics getInstrumentWiseStatistics(@PathVariable String instrument_identifier) {

		Statistics statistics = TicksServiceImpl.statisticsInstrumentService.getStatistics(instrument_identifier);
		return statistics;
	}

}
