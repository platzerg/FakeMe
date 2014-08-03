package com.platzerworld.fakefb.utils.actions;

import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetInvitableFriendsAction  extends GetFriendsAction {

	public GetInvitableFriendsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.INVITABLE_FRIENDS);
	}

}
