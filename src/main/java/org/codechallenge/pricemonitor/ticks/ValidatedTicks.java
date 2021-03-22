package org.codechallenge.pricemonitor.ticks;

import lombok.Value;

import javax.validation.constraints.*;

/**
 * DTO Class including validation logic.
 *
 * @author Mohsin Khan
 * 
 */

@Value
public class ValidatedTicks {

	@NotBlank
	String instrument;
	@Positive
	double price;
	@Positive
	long timestamp;

}
