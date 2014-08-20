package com.platzerworld.foursquare.listeners;

import com.platzerworld.foursquare.models.Venue;

public interface FoursquareVenueDetailsRequestListener extends ErrorListener {

    public void onVenueDetailFetched(Venue venues);

}
