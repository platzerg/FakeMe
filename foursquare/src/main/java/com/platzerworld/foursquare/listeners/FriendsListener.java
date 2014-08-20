package com.platzerworld.foursquare.listeners;

import com.platzerworld.foursquare.models.User;

import java.util.ArrayList;


public interface FriendsListener extends ErrorListener {

	public void onGotFriends(ArrayList<User> list);
	
}
