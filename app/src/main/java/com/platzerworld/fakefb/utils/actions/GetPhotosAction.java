package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Photo;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetPhotosAction extends GetAction<List<Photo>> {

	public GetPhotosAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.PHOTOS;
	}

	@Override
	protected List<Photo> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Photo> photos = new ArrayList<Photo>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Photo photo = Photo.create(graphObject);
			photos.add(photo);
		}
		return photos;
	}

}
