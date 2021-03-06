package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Notification;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetNotificationsAction extends GetAction<List<Notification>> {

	public GetNotificationsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.NOTIFICATIONS);
	}

	@Override
	protected List<Notification> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Notification> notifications = new ArrayList<Notification>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Notification notification = Notification.create(graphObject);
			notifications.add(notification);
		}
		return notifications;
	}

}
