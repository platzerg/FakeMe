package com.platzerworld.fakefb.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.platzerworld.fakefb.R;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.entities.Photo;
import com.platzerworld.fakefb.utils.entities.Privacy;
import com.platzerworld.fakefb.utils.entities.Privacy.PrivacySettings;
import com.platzerworld.fakefb.utils.fb.SimpleFacebook;
import com.platzerworld.fakefb.utils.listeners.OnPublishListener;

public class PublishPhotoFragment extends BaseFragment {

	private final static String EXAMPLE = "Publish photo";

	private TextView mResult;
	private Button mButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(EXAMPLE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_example_action, container, false);
		mResult = (TextView) view.findViewById(R.id.result);
		view.findViewById(R.id.load_more).setVisibility(View.GONE);
		mButton = (Button) view.findViewById(R.id.button);
		mButton.setText(EXAMPLE);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final Bitmap bitmap = Utils.takeScreenshot(getActivity());

				// set privacy
				Privacy privacy = new Privacy.Builder()
					.setPrivacySettings(PrivacySettings.ALL_FRIENDS)
					.build();

				// create Photo instance and add some properties
				Photo photo = new Photo.Builder()
					.setImage(bitmap)
					.setName("Screenshot from #android_simple_facebook sample application")
					.setPlace("110619208966868")
					.setPrivacy(privacy)
					.build();

				SimpleFacebook.getInstance().publish(photo, new OnPublishListener() {

					@Override
					public void onException(Throwable throwable) {
						hideDialog();
						mResult.setText(throwable.getMessage());
					}

					@Override
					public void onFail(String reason) {
						hideDialog();
						mResult.setText(reason);
					}

					@Override
					public void onThinking() {
						showDialog();
					}

					@Override
					public void onComplete(String response) {
						hideDialog();
						mResult.setText(response);
					}
				});

			}
		});
		return view;
	}

}
