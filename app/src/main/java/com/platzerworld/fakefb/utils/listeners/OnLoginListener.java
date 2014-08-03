package com.platzerworld.fakefb.utils.listeners;

import com.platzerworld.fakefb.utils.fb.Permission;

import android.app.Activity;

/**
 * On login/logout actions listener
 * 
 * @author sromku
 */
public interface OnLoginListener extends OnThinkingListetener {
	/**
	 * If user performed {@link FacebookTools#login(android.app.Activity)} action, this
	 * callback method will be invoked
	 */
	void onLogin();

	/**
	 * If user pressed 'cancel' in one of the permissions dialog (READ or
	 * PUBLISH)
	 */
	void onNotAcceptingPermissions(Permission.Type type);
}