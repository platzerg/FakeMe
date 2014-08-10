/**
 * 
 */
package com.platzerworld.fakefb.places.result;

import java.util.List;

import com.google.api.client.util.Key;
import com.platzerworld.fakefb.places.models.Photo;

/**
 * @author Giuseppe Mastroeni - aka: Kataklisma E-Mail: m.giuseppe@a2plab.com
 * 
 */
public class PhotoResult extends Result {

    @Key
    private List<Photo> photos;

    /*
     * (non-Javadoc)
     * 
     * @see com.a2plab.googleplaces.result.Result#getResults()
     */
    @Override
    public List<Photo> getResults() {
        return photos;
    }

}
