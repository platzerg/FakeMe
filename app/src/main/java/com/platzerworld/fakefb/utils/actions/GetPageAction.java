package com.platzerworld.fakefb.utils.actions;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.entities.Page;
import com.platzerworld.fakefb.utils.entities.Page.Properties;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetPageAction extends GetAction<Page> {

	private Properties mProperties;

	public GetPageAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setProperties(Properties properties) {
		mProperties = properties;
	}

	@Override
	protected String getGraphPath() {
		return getTarget();
	}

	@Override
	protected Bundle getBundle() {
		if (mProperties != null) {
			return mProperties.getBundle();
		}
		return null;
	}

	@Override
	protected Page processResponse(Response response) {
		GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
		Page page = Page.create(graphObject);
		return page;
	}

}
