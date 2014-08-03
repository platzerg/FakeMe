package com.platzerworld.fakefb.utils.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.platzerworld.fakefb.utils.GraphPath;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Account;
import com.platzerworld.fakefb.utils.fb.SessionManager;

public class GetAccountsAction extends GetAction<List<Account>> {

	public GetAccountsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.ACCOUNTS;
	}

	@Override
	protected List<Account> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Account> groups = new ArrayList<Account>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Account group = Account.create(graphObject);
			groups.add(group);
		}
		return groups;
	}

}
