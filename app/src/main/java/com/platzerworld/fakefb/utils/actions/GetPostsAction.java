package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Post;
import com.platzerworld.fakefb.utils.entities.Post.PostType;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetPostsAction extends GetAction<List<Post>> {

	private PostType mPostType = PostType.ALL; // default

	public GetPostsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setPostType(PostType postType) {
		mPostType = postType;
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + mPostType.getGraphPath();
	}

	@Override
	protected List<Post> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Post> posts = new ArrayList<Post>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Post post = Post.create(graphObject);
			posts.add(post);
		}
		return posts;
	}

}
