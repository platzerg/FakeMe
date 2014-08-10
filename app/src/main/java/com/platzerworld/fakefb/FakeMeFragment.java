package com.platzerworld.fakefb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.platzerworld.fakefb.fragments.BaseFragment;
import com.platzerworld.fakefb.places.GooglePlaces;
import com.platzerworld.fakefb.places.jackson.PlaceSearchResponseVO;
import com.platzerworld.fakefb.places.result.Result;
import com.platzerworld.fakefb.utils.parser.PlaceDetailsJSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

public class FakeMeFragment extends BaseFragment {
	private UiLifecycleHelper uiHelper;
	private TextView txtLongitude;
	private TextView txtLatitude;
	
	private TextView mtxtResult;
	
	private Button btnReset;
	private Button btnCheckFacebook;
	private Button btnDoFacebook;


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
				//makeMyFriendsRequest();
                showMap();
			}
		});
		
		btnDoFacebook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//callFB();
                // http://www.taywils.me/2013/05/06/androidgoogleplacestutorial.html
                http://nikshits.wordpress.com/2014/03/26/find-address-as-per-input-using-google-places-api-in-android/
                showPlaces();
			}
		});
		return view;
	}

    private void showMap(){
        GooglePlaces googlePlaces = new GooglePlaces("AIzaSyD16oJOQ6USd_SKMCjHnLX6Oc8CkXiBpiQ");
        try {
            Result res = googlePlaces.getNearbyPlaces(49.240635, 12.673337);
            res.getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPlaces(){
        PlacesTask placesTask = new PlacesTask();
        placesTask.execute("Cham");
    }

        /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
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

    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";
            try {

                // Obtain browser key from https://code.google.com/apis/console

                String key = getResources().getString(R.string.google_play_browser);
                String input = "";
                input = "input=" + URLEncoder.encode(place[0], "utf8");
                // place type to be searched
                String types = "types=geocode";

                // Sensor enabled
                String sensor = "sensor=false";

                // Building the parameters to the web service
                String parameters = input + "&" + types + "&" + sensor + "&" + key;

                // Output format
                String output = "json";

                // Building the url to the web service
                String url = "https://maps.googleapis.com/maps/api/place/autocomplete/" + output + "?" + parameters;

                String longi = "49.240635"; // txtLongitude.getText().toString();
                String lati = "12.673337"; // txtLatitude.getText().toString();

                String nearby = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

                String radius = "&radius=10000";
                String sensorNearby = "&sensor=true";
                String typesNearby = "&types=food|bar|store|museum|art_gallery&";


                //49.240635, 12.673337


                String location = String.format("?location=%s,%s", longi,lati);
                Log.d("GPL", location);
                nearby+=location;
                nearby+=radius;
                nearby+=sensorNearby;
                nearby+=typesNearby;
                nearby+=key;
                try {
                    data = downloadUrl(nearby);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                Log.d("GPL","result");

                ObjectMapper mapper = new ObjectMapper();
                PlaceSearchResponseVO searchResponseVO = mapper.readValue(result, PlaceSearchResponseVO.class);
                System.out.println(searchResponseVO.getStatus());

                JSONObject reader = new JSONObject(result);
                JSONArray jsonArray = reader.getJSONArray("html_attributions");

                JSONArray resultArray = reader.getJSONArray("results");

                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject next = jsonArray.getJSONObject(i);
                    next.getJSONObject("geometry");

                    String adresse = next.getString("vicinity");

                    JSONArray typesArray = next.getJSONArray("types");
                }


                String next_page_token = reader.getString("next_page_token");
                String status = reader.getString("status");

                ParserTask parserTask = new ParserTask();

                // Start parsing the Google place details in JSON format
                // Invokes the "doInBackground()" method of the class ParseTask
                //parserTask.execute(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** A class to parse the Google Place Details in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, HashMap<String,String>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected HashMap<String,String> doInBackground(String... jsonData) {

            HashMap<String, String> hPlaceDetails = null;
            PlaceDetailsJSONParser placeDetailsJsonParser = new PlaceDetailsJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                // Start parsing Google place details in JSON format
                hPlaceDetails = placeDetailsJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return hPlaceDetails;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(HashMap<String,String> hPlaceDetails){


            String name = hPlaceDetails.get("name");
            String icon = hPlaceDetails.get("icon");
            String vicinity = hPlaceDetails.get("vicinity");
            String lat = hPlaceDetails.get("lat");
            String lng = hPlaceDetails.get("lng");
            String formatted_address = hPlaceDetails.get("formatted_address");
            String formatted_phone = hPlaceDetails.get("formatted_phone");
            String website = hPlaceDetails.get("website");
            String rating = hPlaceDetails.get("rating");
            String international_phone_number = hPlaceDetails.get("international_phone_number");
            String url = hPlaceDetails.get("url");


            String mimeType = "text/html";
            String encoding = "utf-8";

            String data = 	"<html>"+
                    "<body><img style='float:left' src="+icon+" /><h1><center>"+name+"</center></h1>" +
                    "<br style='clear:both' />" +
                    "<hr  />"+
                    "<p>Vicinity : " + vicinity + "</p>" +
                    "<p>Location : " + lat + "," + lng + "</p>" +
                    "<p>Address : " + formatted_address + "</p>" +
                    "<p>Phone : " + formatted_phone + "</p>" +
                    "<p>Website : " + website + "</p>" +
                    "<p>Rating : " + rating + "</p>" +
                    "<p>International Phone  : " + international_phone_number + "</p>" +
                    "<p>URL  : <a href='" + url + "'>" + url + "</p>" +
                    "</body></html>";

           Log.d("GPL",data);
        }
    }

}