package com.platzerworld.fakefb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.platzerworld.fakefb.fragments.BaseFragment;
import com.platzerworld.flickr.FlickrActivity;
import com.platzerworld.foursquare.PlatzerworldActivity;
import com.platzerworld.instagram.InstagramActivity;
import com.platzerworld.pinterest.DemoMainActivity;
import com.platzerworld.tumblr.TumblrExampleActivity;

public class SplashFragment extends BaseFragment {
	private TextView skipLoginButton;
	private SkipLoginCallback skipLoginCallback;
	private TextView showmeButton;

	private UiLifecycleHelper uiHelper;

	public interface SkipLoginCallback {
		void onSkipLoginPressed();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.splash, container, false);
		printHashKey();
		skipLoginButton = (TextView) view.findViewById(R.id.skip_login_button);
		skipLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (skipLoginCallback != null) {
					skipLoginCallback.onSkipLoginPressed();
				}
			}
		});

		showmeButton = (TextView) view.findViewById(R.id.showme);
		showmeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("GPL", "showmeButton");
				//callFB();
                //showInstagram();
                //showFoursquare();
                //showFlickr();
                //showPinterest();
                showTumblr();
			}
		});

		uiHelper = new UiLifecycleHelper(this.getActivity(), null);
		uiHelper.onCreate(savedInstanceState);

		// callFB();

		return view;
	}

    private void showTumblr(){
        Intent showPinterestIntent = new Intent(getActivity().getApplicationContext(), TumblrExampleActivity.class);
        startActivity(showPinterestIntent);
    }

    private void showPinterest(){
        Intent showPinterestIntent = new Intent(getActivity().getApplicationContext(), DemoMainActivity.class);
        startActivity(showPinterestIntent);
    }

    private void showFlickr(){
        Intent showFlickrIntent = new Intent(getActivity().getApplicationContext(), FlickrActivity.class);
        startActivity(showFlickrIntent);
    }

    private void showFoursquare(){
        Intent showFoursquareIntent = new Intent(getActivity().getApplicationContext(), PlatzerworldActivity.class);
        startActivity(showFoursquareIntent);
    }

    private void showInstagram(){
        Intent showInstagramIntent = new Intent(getActivity().getApplicationContext(), InstagramActivity.class);
        startActivity(showInstagramIntent);
    }

	private void printHashKey(){
		// Add code to print out the key hash
	    try {
	        PackageInfo info = this.getActivity().getPackageManager().getPackageInfo(
	                "com.facebook.samples.hellofacebook", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("GPL:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }
	}
	private void callFB() {
		// start Facebook Login
		Session.openActiveSession(this.getActivity(), true,
				new Session.StatusCallback() {

					// callback when session changes state
					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						if (session.isOpened()) {
							Log.d("GPL", "call");
							Log.d("GPL", session.getAccessToken());

							/*
							if (FacebookDialog.canPresentShareDialog(SplashFragment.this.getActivity().getApplicationContext(), FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
								FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(SplashFragment.this.getActivity())
				                  .setLink("https://developers.facebook.com/android")
				                  .setPlace("208161819205473")
				                  .build();
				                  uiHelper.trackPendingDialogCall(shareDialog.present());

							}else{
								Log.d("GPL", "not good");
							}
*/
							/*
							 * Request.newMyFriendsRequest(session, new
							 * Request.GraphUserListCallback() {
							 * 
							 * @Override public void onCompleted(List<GraphUser>
							 * users, Response response) { // TODO
							 * Auto-generated method stub Log.d("GPL",
							 * "SplashFragment.callFB friends"); } });
							 * 
							 * // make request to the /me API
							 * Request.newMeRequest(session, new
							 * Request.GraphUserCallback() {
							 * 
							 * // callback after Graph API response with user
							 * object
							 * 
							 * @Override public void onCompleted(GraphUser user,
							 * Response response) { if (user != null) {
							 * 
							 * Log.d("GPL", "SplashFragment.callFB"); } }
							 * }).executeAsync();
							 */

						}
					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("GPL",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("GPL", "Success!");
					}
				});
		Session.getActiveSession().onActivityResult(this.getActivity(),
				requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	public void setSkipLoginCallback(SkipLoginCallback callback) {
		skipLoginCallback = callback;
	}
}