package com.platzerworld.foursquare;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.platzerworld.foursquare.criterias.CheckInCriteria;
import com.platzerworld.foursquare.criterias.TipsCriteria;
import com.platzerworld.foursquare.listeners.AccessTokenRequestListener;
import com.platzerworld.foursquare.listeners.CheckInListener;
import com.platzerworld.foursquare.listeners.ImageRequestListener;
import com.platzerworld.foursquare.listeners.TipsRequestListener;
import com.platzerworld.foursquare.listeners.UserInfoRequestListener;
import com.platzerworld.foursquare.models.Checkin;
import com.platzerworld.foursquare.models.Tip;
import com.platzerworld.foursquare.models.User;
import com.platzerworld.foursquare.tasks.users.UserImageRequest;

import java.util.ArrayList;
import java.util.List;

import com.platzerworld.foursquare.R;


public class PlatzerworldActivity extends Activity {

    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platzerworld);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.platzerworld, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment  implements AccessTokenRequestListener, ImageRequestListener{
        private ImageView userImage;
        private EasyFoursquareAsync async;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_platzerworld, container, false);

            final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString("foursquare_client_id", "PU305DK3MQPVEVD5X253UKVEB3FLIO0QGRGTDPOGSHR0ZNGF");
            editor.putString("foursquare_client_secret", "HJTTM1VDE00UQ4WOM1JBZXECRHC1GIUXP3XNR0LFVG2VMM3I");
            editor.commit();

            userImage = (ImageView) rootView.findViewById(R.id.imageView1)   ;

            Button btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EasyFoursquareAsync async = new EasyFoursquareAsync(PlaceholderFragment.this.getActivity());
                    async.requestAccess(PlaceholderFragment.this);
                }
            });

            return rootView;
        }
        @Override
        public void onError(String errorMsg) {
            // Do something with the error message
            Toast.makeText(this.getActivity(), errorMsg, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAccessGrant(String accessToken) {
            // with the access token you can perform any request to foursquare.
            // example:
            final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString("foursquare_access_token", accessToken);
            editor.commit();

            EasyFoursquareAsync async = new EasyFoursquareAsync(PlaceholderFragment.this.getActivity());
            async.getUserInfo(new UserInfoRequestListener() {

                public void onError(String errorMsg) {
                    Log.e("GPL", errorMsg);
                }

                public void onUserInfoFetched(User user) {
                    // OWww. did i already got user!?
                    if (user.getBitmapPhoto() == null) {
                        UserImageRequest request = new UserImageRequest(PlaceholderFragment.this.getActivity());
                        request.execute(user.getPhoto());
                    } else {
                        userImage.setImageBitmap(user.getBitmapPhoto());
                    }
                    Log.d("GPL", "got it");
                }
            });

            //for another examples uncomment lines below:
            //requestTipsNearby();
            //checkin();
            requestUserInfo(); // 4d4aaa1311a36ea80894331c    14295213
        }

        @Override
        public void onImageFetched(Bitmap bmp) {
            userImage.setImageBitmap(bmp);
        }

        private void checkin() {

            CheckInCriteria criteria = new CheckInCriteria();
            criteria.setBroadcast(CheckInCriteria.BroadCastType.PUBLIC);
            criteria.setVenueId("4c7063da9c6d6dcb9798d27a");
            EasyFoursquareAsync async = new EasyFoursquareAsync(PlaceholderFragment.this.getActivity());
            async.checkIn(new CheckInListener() {
                @Override
                public void onCheckInDone(Checkin checkin) {
                    Toast.makeText(PlaceholderFragment.this.getActivity(), checkin.getId(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(String errorMsg) {
                    Toast.makeText(PlaceholderFragment.this.getActivity(), "error", Toast.LENGTH_LONG).show();
                }
            }, criteria);

        }

        private void requestTipsNearby() {
            Location loc = new Location("");
            loc.setLatitude(48.180184);
            loc.setLongitude(11.592094);

            TipsCriteria criteria = new TipsCriteria();
            criteria.setLocation(loc);
            EasyFoursquareAsync async = new EasyFoursquareAsync(PlaceholderFragment.this.getActivity());
            async.getTipsNearby(new TipsRequestListener() {

                @Override
                public void onError(String errorMsg) {
                    Log.e("GPL", errorMsg);
                }

                @Override
                public void onTipsFetched(ArrayList<Tip> tips) {
                    for(Tip place : tips){
                        Log.d("GPL", "TipId: " +place.getId() + " - Venue: " + place.getVenue().toString());
                    }
                }
            }, criteria);
        }

        private void requestUserInfo(){
            // 14295213
            EasyFoursquareAsync async = new EasyFoursquareAsync(PlaceholderFragment.this.getActivity());
            async.getUserInfo(new UserInfoRequestListener() {
                @Override
                public void onUserInfoFetched(User user) {
                    Log.e("GPL", user.getLastName());
                }

                @Override
                public void onError(String errorMsg) {
                    Log.e("GPL", errorMsg);
                }
            });
        }

    }


}
