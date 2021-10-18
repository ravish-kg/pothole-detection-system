package com.example.ravish.pothole_detection;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Ravish on 3/7/2017.
 */
public class EachPothole implements ClusterItem {

    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;

    public EachPothole(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
        mTitle = null;
        mSnippet = null;
    }

    public EachPothole(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    public LatLng getPosition() {
        return mPosition;
    }

    public String getTitle() {
        return mTitle; }

    public String getSnippet() {
        return mSnippet; }

    /**
     * Set the title of the marker
     * @param title string to be set as title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Set the description of the marker
     * @param snippet string to be set as snippet
     */
    public void setSnippet(String snippet) {
        mSnippet = snippet;
    }
}
