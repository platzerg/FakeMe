package com.platzerworld.fakefb.utils.entities;

import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.Utils;

public class Like {

	private User mUser;

	private Like(GraphObject graphObject) {
		mUser = Utils.createUser(graphObject);
	}

	public static Like create(GraphObject graphObject) {
		return new Like(graphObject);
	}

	public User getUser() {
		return mUser;
	}
}
