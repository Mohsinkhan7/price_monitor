package org.codechallenge.pricemonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Class for the Price Monitor Application
 * 
 * @author Mohsin Khan
 */


@SpringBootApplication
@EnableScheduling
public class PriceMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceMonitorApplication.class, args);
	}

}
