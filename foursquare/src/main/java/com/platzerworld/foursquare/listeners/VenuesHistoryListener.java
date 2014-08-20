package com.platzerworld.foursquare.listeners;

import com.platzerworld.foursquare.models.Venues;

import java.util.ArrayList;

public interface VenuesHistoryListener extends ErrorListener {

	public void onGotVenuesHistory(ArrayList<Venues> list);

}
