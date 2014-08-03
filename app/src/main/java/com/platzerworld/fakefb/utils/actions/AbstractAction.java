package com.platzerworld.fakefb.utils.actions;

import com.platzerworld.fakefb.utils.fb.SessionManager;
import com.platzerworld.fakefb.utils.fb.SimpleFacebook;
import com.platzerworld.fakefb.utils.fb.SimpleFacebookConfiguration;

public abstract class AbstractAction {

	protected SessionManager sessionManager;
	protected SimpleFacebookConfiguration configuration = SimpleFacebook.getConfiguration();

	public AbstractAction(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void execute() {
		executeImpl();
	}

	protected abstract void executeImpl();
}
