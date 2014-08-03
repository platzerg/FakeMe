package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Like;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetLikesAction extends GetAction<List<Like>> {

	public GetLikesAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.LIKES;
	}

	@Override
	protected List<Like> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Like> likes = new ArrayList<Like>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Like like = Like.create(graphObject);
			likes.add(like);
		}
		return likes;
	}
}
