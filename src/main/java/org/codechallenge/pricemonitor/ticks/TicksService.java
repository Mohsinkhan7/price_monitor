package org.codechallenge.pricemonitor.ticks;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;


/**
 * Interface class to decide business logic for the Tick Service.
 *
 * @author Mohsin Khan
 */

public interface TicksService {

	HttpStatus recieveInupt(@Valid ValidatedTicks validatedTicks);

}
