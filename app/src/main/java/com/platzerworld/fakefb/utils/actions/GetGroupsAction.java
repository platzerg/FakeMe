package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Group;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetGroupsAction extends GetAction<List<Group>> {

	public GetGroupsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.GROUPS;
	}

	@Override
	protected List<Group> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Group> groups = new ArrayList<Group>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Group group = Group.create(graphObject);
			groups.add(group);
		}
		return groups;
	}

}
