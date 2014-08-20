package com.platzerworld.foursquare;


import android.app.Activity;
import android.content.SharedPreferences;
import com.platzerworld.foursquare.constants.FoursquareConstants;
import com.platzerworld.foursquare.criterias.CheckInCriteria;
import com.platzerworld.foursquare.criterias.TipsCriteria;
import com.platzerworld.foursquare.criterias.TrendingVenuesCriteria;
import com.platzerworld.foursquare.criterias.VenuesCriteria;
import com.platzerworld.foursquare.listeners.AccessTokenRequestListener;
import com.platzerworld.foursquare.listeners.CheckInListener;
import com.platzerworld.foursquare.listeners.FoursquareTrendingVenuesRequestListener;
import com.platzerworld.foursquare.listeners.FoursquareVenueDetailsRequestListener;
import com.platzerworld.foursquare.listeners.FoursquareVenuesRequestListener;
import com.platzerworld.foursquare.listeners.FriendsListener;
import com.platzerworld.foursquare.listeners.GetCheckInsListener;
import com.platzerworld.foursquare.listeners.TipsRequestListener;
import com.platzerworld.foursquare.listeners.UserInfoRequestListener;
import com.platzerworld.foursquare.listeners.VenuePhotosListener;
import com.platzerworld.foursquare.listeners.VenuesHistoryListener;
import com.platzerworld.foursquare.tasks.checkins.CheckInRequest;
import com.platzerworld.foursquare.tasks.tips.TipsNearbyRequest;
import com.platzerworld.foursquare.tasks.users.GetCheckInsRequest;
import com.platzerworld.foursquare.tasks.users.GetFriendsRequest;
import com.platzerworld.foursquare.tasks.users.GetUserVenuesHistoryRequest;
import com.platzerworld.foursquare.tasks.users.SelfInfoRequest;
import com.platzerworld.foursquare.tasks.venues.FoursquareTrendingVenuesNearbyRequest;
import com.platzerworld.foursquare.tasks.venues.FoursquareVenueDetailsRequest;
import com.platzerworld.foursquare.tasks.venues.FoursquareVenuesNearbyRequest;
import com.platzerworld.foursquare.tasks.venues.GetVenuePhotosRequest;
/**
 * Class to handle methods used to perform requests to FoursquareAPI and respond
 * ASYNChronously.
 *
 * @author Felipe Conde <condesales@gmail.com>
 */
public class EasyFoursquareAsync {

    private Activity mActivity;
    private FoursquareDialog mDialog;
    private String mAccessToken = "";

    public EasyFoursquareAsync(Activity activity) {
        mActivity = activity;
    }

    /**
     * Requests the access to API
     */
    public void requestAccess(AccessTokenRequestListener listener) {
        if (!hasAccessToken()) {
            loginDialog(listener);
        } else {
            listener.onAccessGrant(getAccessToken());
        }
    }

    /**
     * Revokes the access to API
     */
    public void revokeAccess() {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(FoursquareConstants.SHARED_PREF_FILE, 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(FoursquareConstants.ACCESS_TOKEN);
        editor.commit();
    }

    /**
     * Requests logged user information asynchronously.
     *
     * @param listener As the request is asynchronous, listener used to retrieve the
     *                 User object, containing the information.
     */
    public void getUserInfo(UserInfoRequestListener listener) {
        SelfInfoRequest request = new SelfInfoRequest(mActivity, listener);
        request.execute(getAccessToken());
    }

    /**
     * Requests the nearby Venues.
     *
     * @param criteria The criteria to your search request
     * @param listener As the request is asynchronous, listener used to retrieve the
     *                 User object, containing the information.
     */
    public void getVenuesNearby(FoursquareVenuesRequestListener listener,
                                VenuesCriteria criteria) {
        FoursquareVenuesNearbyRequest request = new FoursquareVenuesNearbyRequest(
                mActivity, listener, criteria);
        request.execute(getAccessToken());
    }

    /**
     * Requests the nearby Tips.
     *
     * @param criteria The criteria to your search request
     * @param listener As the request is asynchronous, listener used to retrieve the
     *                 User object, containing the information.
     */
    public void getTipsNearby(TipsRequestListener listener,
                              TipsCriteria criteria) {
        TipsNearbyRequest request = new TipsNearbyRequest(
                mActivity, listener, criteria);
        request.execute(getAccessToken());
    }

    /**
     * Requests the nearby Venus that are trending.
     *
     * @param listener As the request is asynchronous, listener used to retrieve the
     *                 User object, containing the information.
     * @param criteria The criteria to your search request
     */
    public void getTrendingVenuesNearby(FoursquareTrendingVenuesRequestListener listener, TrendingVenuesCriteria criteria) {
        FoursquareTrendingVenuesNearbyRequest request = new FoursquareTrendingVenuesNearbyRequest(mActivity, listener, criteria);
        request.execute(getAccessToken());

    }


    public void getVenueDetail(String venueID, FoursquareVenueDetailsRequestListener listener) {
        FoursquareVenueDetailsRequest request = new FoursquareVenueDetailsRequest(mActivity, listener, venueID);
        request.execute(getAccessToken());
    }


    /**
     * Checks in at a venue.
     *
     * @param listener As the request is asynchronous, listener used to retrieve the
     *                 User object, containing the information about the check in.
     * @param criteria The criteria to your search request
     */
    public void checkIn(CheckInListener listener, CheckInCriteria criteria) {
        CheckInRequest request = new CheckInRequest(mActivity, listener,
                criteria);
        request.execute(getAccessToken());
    }

    public void getCheckIns(GetCheckInsListener listener) {
        GetCheckInsRequest request = new GetCheckInsRequest(mActivity, listener);
        request.execute(getAccessToken());
    }

    public void getCheckIns(GetCheckInsListener listener, String userID) {
        GetCheckInsRequest request = new GetCheckInsRequest(mActivity,
                listener, userID);
        request.execute(getAccessToken());
    }

    public void getFriends(FriendsListener
                                   listener) {
        GetFriendsRequest request = new GetFriendsRequest(mActivity, listener);
        request.execute(mAccessToken);
    }

    public void getFriends(FriendsListener listener, String userID) {
        GetFriendsRequest request = new GetFriendsRequest(mActivity, listener,
                userID);
        request.execute(getAccessToken());
    }

    public void getVenuesHistory(VenuesHistoryListener listener) {
        GetUserVenuesHistoryRequest request = new GetUserVenuesHistoryRequest(
                mActivity, listener);
        request.execute(getAccessToken());
    }

    public void getVenuesHistory(VenuesHistoryListener listener, String userID) {
        GetUserVenuesHistoryRequest request = new GetUserVenuesHistoryRequest(
                mActivity, listener, userID);
        request.execute(getAccessToken());
    }

    public void getVenuePhotos(String venueID, VenuePhotosListener listener) {
        GetVenuePhotosRequest request = new GetVenuePhotosRequest(mActivity, listener, venueID);
        request.execute(getAccessToken());
    }

    private boolean hasAccessToken() {
        String token = getAccessToken();
        return !token.equals("");
    }

    private String getAccessToken() {
        if (mAccessToken.equals("")) {
            SharedPreferences settings = mActivity.getSharedPreferences(
                    FoursquareConstants.SHARED_PREF_FILE, 0);
            mAccessToken = settings.getString(FoursquareConstants.ACCESS_TOKEN,
                    "");
        }
        return mAccessToken;
    }

    /**
     * Requests the Foursquare login though a dialog.
     */
    private void loginDialog(AccessTokenRequestListener listener) {
        String url = "https://foursquare.com/oauth2/authenticate"
                + "?client_id=" + FoursquareConstants.CLIENT_ID
                + "&response_type=code" + "&redirect_uri="
                + FoursquareConstants.CALLBACK_URL;

        mDialog = new FoursquareDialog(mActivity, url, listener);
        mDialog.show();
    }

}
