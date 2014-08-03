package com.platzerworld.fakefb;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.platzerworld.fakefb.fragments.BaseFragment;

public class FakeMeFragment extends BaseFragment {
	private UiLifecycleHelper uiHelper;
	private TextView txtLongitude;
	private TextView txtLatitude;
	
	private TextView mtxtResult;
	
	private Button btnReset;
	private Button btnCheckFacebook;
	private Button btnDoFacebook;
	
	private FragmentManager manager;

	private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    
	public interface SkipLoginCallback {
		void onSkipLoginPressed();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fakeme, container, false);
		manager = getActivity().getSupportFragmentManager();
		
		uiHelper = new UiLifecycleHelper(this.getActivity(), null);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		
		uiHelper.onCreate(savedInstanceState);
		
		txtLatitude = (TextView) view.findViewById(R.id.txtlatitude);
		txtLongitude = (TextView) view.findViewById(R.id.txtlongitude);
		
		mtxtResult = (TextView) view.findViewById(R.id.mtxtresult);
		
		btnReset = (Button) view.findViewById(R.id.btnreset);
		btnCheckFacebook = (Button) view.findViewById(R.id.btncheckfaceboo);
		btnDoFacebook = (Button) view.findViewById(R.id.btndofaceboo);
		

		btnReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				txtLongitude.setText("");
				txtLatitude.setText("");
				mtxtResult.setText("");
			}
		});
		btnCheckFacebook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				makeMyFriendsRequest();
			}
		});
		
		btnDoFacebook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callFB();
			}
		});
		return view;
	}
	
	private void makeMeRequest() {
		Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
        	Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                    	mtxtResult.setText(user.toString());
                    	Log.d("GPL", "success");
                    }
                    if (response.getError() != null) {
                        Log.d("GPL", "makeMeRequest");
                    }
                }
            });
            request.executeAsync();
        }
    }
	
	private void makeMyFriendsRequest() {
		Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
        	Request request = Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
                @Override
                public void onCompleted(List<GraphUser> users, Response response) {
                    if (users != null) {
                    	int size = users.size();
                    	mtxtResult.setText("users.size()");
                    	Log.d("GPL", "sucess");
                    }
                    if (response.getError() != null) {
                        Log.d("GPL", "makeMyFriendsRequest");
                    }
                }
            });
            request.executeAsync();
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
	
	private void onError(Exception error) {
        String text = getString(R.string.exception, error.getMessage());
        Toast toast = Toast.makeText(this.getActivity(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
	
	private void finishActivity() {
        FakeMeApplication app = (FakeMeApplication) getActivity().getApplication();
        Log.d("GPL", "app");
        
        getActivity().setResult(1, null);
        getActivity().finish();
    }
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        Log.d("GPL", "onSessionStateChange");
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
}