package com.platzerworld.foursquare.listeners;

import com.platzerworld.foursquare.models.User;

public interface UserInfoRequestListener extends ErrorListener {

	public void onUserInfoFetched(User user);
}
