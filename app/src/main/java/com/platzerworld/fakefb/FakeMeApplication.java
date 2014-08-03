/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.platzerworld.fakefb;

import java.util.List;

import android.app.Application;

import com.facebook.SessionDefaultAudience;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.platzerworld.fakefb.utils.Logger;
import com.platzerworld.fakefb.utils.SharedObjects;
import com.platzerworld.fakefb.utils.fb.Permission;
import com.platzerworld.fakefb.utils.fb.SimpleFacebook;
import com.platzerworld.fakefb.utils.fb.SimpleFacebookConfiguration;

/**
 * Use a custom Application class to pass state data between Activities.
 */
public class FakeMeApplication extends Application {
	
	private static final String APP_ID = "747214031984432";
	private static final String APP_NAMESPACE = "fake_me";
	
	@Override
	public void onCreate() {
		super.onCreate();
		SharedObjects.context = this;

		// set log to true
		Logger.DEBUG_WITH_STACKTRACE = true;

		// initialize facebook configuration
		Permission[] permissions = new Permission[] { 
				Permission.PUBLIC_PROFILE, 
				Permission.USER_GROUPS,
				Permission.USER_BIRTHDAY, 
				Permission.USER_LIKES, 
				Permission.USER_PHOTOS,
				Permission.USER_VIDEOS,
				Permission.USER_FRIENDS,
				Permission.USER_EVENTS,
				Permission.USER_VIDEOS,
				Permission.USER_RELATIONSHIPS,
				Permission.READ_STREAM, 
				Permission.PUBLISH_ACTION
				};

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
			.setAppId(APP_ID)
			.setNamespace(APP_NAMESPACE)
			.setPermissions(permissions)
			.setDefaultAudience(SessionDefaultAudience.FRIENDS)
			.setAskForAllPermissionsAtOnce(false)
			.build();

		SimpleFacebook.setConfiguration(configuration);
	}

    private List<GraphUser> selectedUsers;
    private GraphPlace selectedPlace;

    public List<GraphUser> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<GraphUser> users) {
        selectedUsers = users;
    }

    public GraphPlace getSelectedPlace() {
        return selectedPlace;
    }

    public void setSelectedPlace(GraphPlace place) {
        this.selectedPlace = place;
    }
}
