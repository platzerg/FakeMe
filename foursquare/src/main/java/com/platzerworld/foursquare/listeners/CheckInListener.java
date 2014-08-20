package com.platzerworld.foursquare.listeners;


import com.platzerworld.foursquare.models.Checkin;

public interface CheckInListener extends ErrorListener {

	public void onCheckInDone(Checkin checkin);
	
}
