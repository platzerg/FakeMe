package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Comment;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetCommentsAction extends GetAction<List<Comment>> {

	public GetCommentsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.COMMENTS;
	}

	@Override
	protected List<Comment> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Comment> comments = new ArrayList<Comment>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Comment comment = Comment.create(graphObject);
			comments.add(comment);
		}
		return comments;
	}

}
