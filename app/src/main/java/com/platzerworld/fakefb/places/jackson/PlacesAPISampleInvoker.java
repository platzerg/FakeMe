package com.platzerworld.fakefb.places.jackson;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;


public class PlacesAPISampleInvoker {
	
	/**
	 * Places API URL request. Please replace the API_ACCESS_KEY with the your specific key from the Google API Console.
	 */
	private static final String PLACES_API_SEARCH_REQUEST_URL = "https://maps.googleapis.com/maps/api/place/search/json?" +
			"sensor=false&location=19.03304880,73.02966250&radius=10000&types=bar&key=AIzaSyD16oJOQ6USd_SKMCjHnLX6Oc8CkXiBpiQ";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			URL url = new URL(PLACES_API_SEARCH_REQUEST_URL);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			ObjectMapper mapper = new ObjectMapper();
			PlaceSearchResponseVO searchResponseVO = mapper.readValue(connection.getInputStream(), PlaceSearchResponseVO.class);
			System.out.println(searchResponseVO.getStatus());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
