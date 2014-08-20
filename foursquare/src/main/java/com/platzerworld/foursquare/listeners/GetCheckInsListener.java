package com.platzerworld.foursquare.listeners;

import com.platzerworld.foursquare.models.Checkin;

import java.util.ArrayList;


public interface GetCheckInsListener extends ErrorListener {

	public void onGotCheckIns(ArrayList<Checkin> list);
	
}
