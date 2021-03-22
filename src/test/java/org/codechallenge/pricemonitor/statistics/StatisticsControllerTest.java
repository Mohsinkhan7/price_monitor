package org.codechallenge.pricemonitor.statistics;

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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Unit Testing for Statistics Controller
 *
 * @author Mohsin Khan
 * 
 */


@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {

	@MockBean
	private StatisticsService StatisticsService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<Statistics> jsonResultAttempt;

	@Test
	void getStatistics() throws Exception {

		// given
		Statistics statistics = new Statistics();

		given(StatisticsService.getStatistics())
		.willReturn(statistics);

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/statistics").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		//then
		then(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		//expected = "{\"avg\":0.0,\"max\":0.0,\"min\":0.0,\"count\":0}";
		then(response.getContentAsString()).isEqualTo(
				jsonResultAttempt.write(
						statistics
						).getJson());

	}


}
