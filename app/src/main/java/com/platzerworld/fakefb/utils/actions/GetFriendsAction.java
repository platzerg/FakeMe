package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Profile;
import com.platzerworld.fakefb.utils.entities.Profile.Properties;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetFriendsAction extends GetAction<List<Profile>> {

	private Properties mProperties;

	public GetFriendsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setProperties(Properties properties) {
		mProperties = properties;
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.FRIENDS);
	}

	@Override
	protected Bundle getBundle() {
		if (mProperties != null) {
			return mProperties.getBundle();
		}
		return null;
	}

	@Override
	protected List<Profile> processResponse(Response response) {
		List<GraphUser> graphUsers = Utils.typedListFromResponse(response, GraphUser.class);
		List<Profile> profiles = new ArrayList<Profile>(graphUsers.size());
		for (GraphUser graphUser : graphUsers) {
			profiles.add(Profile.create(graphUser));
		}
		return profiles;
	}

}
