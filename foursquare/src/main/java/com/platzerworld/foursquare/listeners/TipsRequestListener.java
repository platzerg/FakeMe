package com.platzerworld.foursquare.listeners;

import com.platzerworld.foursquare.models.Tip;

import java.util.ArrayList;

public interface TipsRequestListener extends ErrorListener {

    public void onTipsFetched(ArrayList<Tip> tips);

}
