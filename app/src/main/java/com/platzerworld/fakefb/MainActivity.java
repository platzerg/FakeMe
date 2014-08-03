package com.platzerworld.fakefb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platzerworld.fakefb.fragments.BaseFragment;
import com.platzerworld.fakefb.fragments.MainFragment;
import com.platzerworld.fakefb.utils.Utils;
import com.platzerworld.fakefb.utils.fb.SimpleFacebook;


public class MainActivity extends BaseFragment {
	protected static final String TAG = MainActivity.class.getName();

	private SimpleFacebook mSimpleFacebook;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSimpleFacebook = SimpleFacebook.getInstance(this.getActivity());

		// test local language
		Utils.updateLanguage(this.getActivity().getApplicationContext(), "en");
		Utils.printHashKey(this.getActivity().getApplicationContext());

		
	}

	@Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this.getActivity());
       
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        
        View view = inflater.inflate(R.layout.activity_main, container, false);
        
        addFragment();
        
        return view;
	}

	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSimpleFacebook.onActivityResult(this.getActivity(), requestCode, resultCode, data);
    }

	private void addFragment() {
		FragmentManager fragmentManager = this.getActivity().getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.frame_layout, new MainFragment());
		fragmentTransaction.commit();
	}

}