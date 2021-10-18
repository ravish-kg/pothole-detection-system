package com.example.ravish.pothole_detection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback{

    MapView mMapView;
    private GoogleMap googleMap;
    private GetLocation gps;
    public double longitude;
    double latitude;
    View rootView;

    private ClusterManager<EachPothole> mClusterManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_map, container, false);
            mMapView = (MapView) rootView.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            showMap();
        }
        else{
            showMap();
        }

        return rootView;
    }

    public void showMap(){

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.clear();
                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                gps = new GetLocation(getActivity());

                if(gps.canGetLocation()){

                    longitude = gps.getLongitude();
                    latitude = gps .getLatitude();

                    // For dropping a marker at a point on the Map
                    LatLng current = new LatLng(latitude, longitude);
                    //googleMap.addMarker(new MarkerOptions().position(current).title("Current Location"));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(current).zoom(4).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    mClusterManager = new ClusterManager<EachPothole>(getActivity(), googleMap);

                    googleMap.setOnCameraIdleListener(mClusterManager);
                    mClusterManager.setAnimation(true);
                    try {
                        Toast.makeText(getActivity(), "reading items", Toast.LENGTH_LONG).show();
                        readItems();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "Problem reading list of markers.", Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(getActivity(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    gps.showSettingsAlert();
                }
            }
        });

    }

    public void onMapReady(GoogleMap map){


    }
    private void readItems() throws JSONException {
       /* InputStream inputStream = getResources().openRawResource(R.raw.radar_search);
        List<EachPothole> items = new MyItemReader().read(inputStream);
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            for (EachPothole item : items) {
                LatLng position = item.getPosition();
                double lat = position.latitude + offset;
                double lng = position.longitude + offset;
                EachPothole offsetItem = new EachPothole(lat, lng);
                mClusterManager.addItem(offsetItem);
            }
        }*/
        // Set some lat/lng coordinates to start with.
        double lat = 12.8866862;
        double lng = 77.6081894;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 50; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            EachPothole offsetItem = new EachPothole(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        gps.stopUsingGPS();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
