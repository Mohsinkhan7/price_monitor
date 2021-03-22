package org.codechallenge.pricemonitor.ticks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Controller class for Ticks that Implements a REST Interface
 *
 * @author Mohsin Khan
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ticks")

public class TicksController {

	private final TicksService ticksService;

	@PostMapping
	ResponseEntity postResult(
			@RequestBody @Valid ValidatedTicks validatedTicks) {

		return new ResponseEntity(ticksService.recieveInupt(validatedTicks));
	}

}
