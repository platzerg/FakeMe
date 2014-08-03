package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Checkin;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetCheckinsAction extends GetAction<List<Checkin>> {

	public GetCheckinsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.CHECKINS);
	}

	@Override
	protected List<Checkin> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Checkin> checkins = new ArrayList<Checkin>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Checkin checkin = Checkin.create(graphObject);
			checkins.add(checkin);
		}
		return checkins;
	}

}
