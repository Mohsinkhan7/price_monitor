package org.codechallenge.pricemonitor.ticks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Unit Testing for Ticks Controller
 *
 * @author Mohsin Khan
 * 
 */

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(TicksController.class)
public class TicksControllerTest {

	@MockBean
	private TicksService ticksService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<ValidatedTicks> jsonRequestAttempt;


	@Test
	void postValidTicks() throws Exception {

		// given
		ValidatedTicks validTick = new ValidatedTicks("ABC", 50.7, System.currentTimeMillis());

		given(ticksService.recieveInupt(eq(validTick)))
		.willReturn(HttpStatus.CREATED);

		// when
		MockHttpServletResponse response = mvc.perform(
				post("/ticks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequestAttempt.write(validTick).getJson()))
				.andReturn().getResponse();

		//then
		then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

	}

	@Test
	void postValidTickOutOfTimeInterval() throws Exception {

		// given
		ValidatedTicks validTick = new ValidatedTicks("ABC", 50.7, 100000);

		given(ticksService.recieveInupt(eq(validTick)))
		.willReturn(HttpStatus.NO_CONTENT);

		// when
		MockHttpServletResponse response = mvc.perform(
				post("/ticks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequestAttempt.write(validTick).getJson()))
				.andReturn().getResponse();

		//then
		then(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());

	}

	@Test
	void postBlankInstrument() throws Exception {

		// given
		ValidatedTicks invalidTick = new ValidatedTicks("", 50.7, System.currentTimeMillis());


		// when
		MockHttpServletResponse response = mvc.perform(
				post("/ticks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequestAttempt.write(invalidTick).getJson()))
				.andReturn().getResponse();

		//then
		then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

	}

	@Test
	void postNegativePrice() throws Exception {

		// given
		ValidatedTicks invalidTick = new ValidatedTicks("DEF", -10, System.currentTimeMillis());


		// when
		MockHttpServletResponse response = mvc.perform(
				post("/ticks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequestAttempt.write(invalidTick).getJson()))
				.andReturn().getResponse();

		//then
		then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

	}

	@Test
	void postInvalidTimeStamp() throws Exception {

		// given
		ValidatedTicks invalidTick = new ValidatedTicks("DEF", 10, 000000000);


		// when
		MockHttpServletResponse response = mvc.perform(
				post("/ticks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequestAttempt.write(invalidTick).getJson()))
				.andReturn().getResponse();

		//then
		then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

	}



}
