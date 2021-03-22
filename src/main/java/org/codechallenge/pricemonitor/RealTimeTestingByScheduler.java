package org.codechallenge.pricemonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Optional Class created for Command Line Testing via Schedular
 * To enable, uncomment the required annotations only
 *  
 * @author Mohsin Khan
 */

//@Component
public class RealTimeTestingByScheduler {

	long priceIncrement=10; //Sample Price Variable, Random can be used while simple provides more in-depth undertanding

	//@Scheduled(fixedRate = 25000)
	//@DependsOn({"TicksController"})
	public void fixedRateSchedulingPostTesting() throws IOException { 

		priceIncrement+=10;
		long currentMilliSeconds = System.currentTimeMillis();
		final String POST_PARAMS = "{\n" + "\"instrument\": \"101\",\r\n" +
				"    \"price\": "+priceIncrement+",\r\n" +
				"    \"timestamp\": "+currentMilliSeconds+"\r\n" + "}";

		URL obj = new URL("http://localhost:8080/ticks");
		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("userId", "MohsinKhan");
		postConnection.setRequestProperty("Content-Type", "application/json");
		postConnection.setDoOutput(true);
		OutputStream os = postConnection.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		postConnection.getResponseCode();
		System.out.println("POST: "+(currentMilliSeconds/1000)+"  "+priceIncrement);

	}

	//@Scheduled(fixedRate = 10000)
	//@DependsOn({"StatisticsController"})
	public void fixedRateSchedulingGetTesting() throws IOException { 
		URL obj2 = new URL("http://localhost:8080/statistics");
		HttpURLConnection postConnection2 = (HttpURLConnection) obj2.openConnection();
		postConnection2.setRequestMethod("GET");
		postConnection2.setRequestProperty("userId", "MohsinKhan");
		postConnection2.setRequestProperty("Content-Type", "application/json");
		postConnection2.getResponseCode();


		try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(postConnection2.getInputStream(), "utf-8"))) {
				    StringBuilder response = new StringBuilder();
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        response.append(responseLine.trim());
				    }
				    System.out.println(response.toString());
				}
		
	}

}
