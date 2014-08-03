package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.AppRequest;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetAppRequestsAction extends GetAction<List<AppRequest>> {

	public GetAppRequestsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.APPREQUESTS);
	}

	@Override
	protected List<AppRequest> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<AppRequest> appRequests = new ArrayList<AppRequest>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			AppRequest graphRequest = AppRequest.create(graphObject);
			appRequests.add(graphRequest);
		}
		return appRequests;
	}

}
