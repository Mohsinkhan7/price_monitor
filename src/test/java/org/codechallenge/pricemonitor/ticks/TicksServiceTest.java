package org.codechallenge.pricemonitor.ticks;

/**
 * Unit Testing for Statistics Controller
 *
 * @author Mohsin Khan
 * 
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.BDDAssertions.then;

public class TicksServiceTest {

	private TicksService ticksService;

	@BeforeEach
	public void setUp() {
		ticksService= new TicksServiceImpl();
	}

	@Test
	void rightInput() throws Exception {

		// given
		ValidatedTicks validTick = new ValidatedTicks("ABC", 50.7, System.currentTimeMillis());


		// when
		HttpStatus response = ticksService.recieveInupt(validTick);

		//then
		then(response).isEqualTo(HttpStatus.CREATED);

	}

	@Test
	void wrongInput() throws Exception {

		// given
		ValidatedTicks validTick = new ValidatedTicks("ABC", 50.7, 1000);


		// when
		HttpStatus response = ticksService.recieveInupt(validTick);

		//then
		then(response).isEqualTo(HttpStatus.NO_CONTENT);

	}

}
