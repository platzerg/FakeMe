package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Album;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetAlbumsAction extends GetAction<List<Album>> {

	public GetAlbumsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.ALBUMS);
	}

	@Override
	protected List<Album> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Album> albums = new ArrayList<Album>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Album album = Album.create(graphObject);
			albums.add(album);
		}
		return albums;
	}

}
