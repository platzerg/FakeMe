package com.platzerworld.foursquare.listeners;

import com.platzerworld.foursquare.models.PhotosGroup;

/**
 * Created by dionysis_lorentzos on 2/8/14.
 * All rights reserved by the Author.
 * Use with your own responsibility.
 */

public interface VenuePhotosListener extends ErrorListener {

    public void onGotVenuePhotos(PhotosGroup photosGroup);

}
