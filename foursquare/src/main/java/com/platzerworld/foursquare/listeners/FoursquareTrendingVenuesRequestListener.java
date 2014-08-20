package com.platzerworld.foursquare.listeners;

import com.platzerworld.foursquare.models.Venue;

import java.util.ArrayList;


public interface FoursquareTrendingVenuesRequestListener extends ErrorListener {

    public void onTrendedVenuesFetched(ArrayList<Venue> venues);

}
