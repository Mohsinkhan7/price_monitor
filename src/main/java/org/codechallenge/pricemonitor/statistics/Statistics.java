package org.codechallenge.pricemonitor.statistics;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

/**
 * This class is to model Statistics object.
 * 
 * @author Mohsin Khan
 */

@Getter
@Setter
@JsonPropertyOrder({ "avg", "max", "min","count" })
public class Statistics {

	private double avg,max,min;
	
	long count;

	public Statistics() {
		avg=0.0; 
		max=0.0;
		min=0.0;
		count=0;
	}

	//Copy Constructor for Initialization
	public Statistics(double price) {
		avg = price;
		max = price;
		min = price;
		count = 1;
	}

	public Statistics(double ag,double mx,double mn,long cnt) {
		avg = ag;
		max = mx;
		min = mn;
		count = cnt;
	}


}
