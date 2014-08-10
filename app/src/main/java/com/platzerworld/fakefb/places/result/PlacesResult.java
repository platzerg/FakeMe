package com.platzerworld.fakefb.places.result;

import java.util.List;

import com.google.api.client.util.Key;
import com.platzerworld.fakefb.places.models.Place;

public class PlacesResult extends Result {

	@Key
	public List<? extends Place> results;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.a2plab.googleplaces.result.Result#getResult()
	 */
	@Override
	public List<? extends Place> getResults() {
		return results;
	}

}
