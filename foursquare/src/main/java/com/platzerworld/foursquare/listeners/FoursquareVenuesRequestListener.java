package com.platzerworld.foursquare.listeners;

import com.platzerworld.foursquare.models.Venue;

import java.util.ArrayList;


public interface FoursquareVenuesRequestListener extends ErrorListener {

    public void onVenuesFetched(ArrayList<Venue> venues);

}
