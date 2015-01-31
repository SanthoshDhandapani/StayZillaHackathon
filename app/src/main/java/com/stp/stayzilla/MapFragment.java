package com.stp.stayzilla;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by thiyaga on 31/1/15.
 */
public class MapFragment extends SupportMapFragment {

    static final LatLng HAMBURG = new LatLng(12.979432, 80.226490);
    static final LatLng KIEL = new LatLng(12.977790, 80.224465);
    ArrayList<LatLng> response;
    ProgressDialog dialog;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View v = super.onCreateView(inflater, container, args);
        ArrayList<LatLng> latLngs = getArguments().getParcelableArrayList("latlng");
        setLatLng(latLngs);
        initMap();
        return v;
    }

    public void setLatLng(ArrayList<LatLng> latlng) {
        response = latlng;
    }

    public void addLatLng(LatLng ltlng) {
        response.add(ltlng);
    }


    public void initMap() {

         googleMap = getMap();
        googleMap.clear();


        for (int i = 0; i < response.size(); i++) {
            //JSONObject kitchen = kitchenList.getJSONObject(i);
            // System.out.println("pradeep hotel = " + kitchen);
            //txtView1.setText(kitchen.getString("displayName"));

            LatLng latLang = new LatLng(response.get(i).latitude,
                    response.get(i).longitude);

            drawMarkerInMap(latLang, BitmapDescriptorFactory.HUE_VIOLET);


        }


    }

    public void drawMarkerInMap(LatLng latLang, float hueBlue) {
        View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.map_marker_layout, null);
        TextView txtView1 = (TextView) marker.findViewById(R.id.marker_text);
        Context context = getActivity().getBaseContext();
        BitmapDrawableFromView bitmapDrawableFromViewInstance = BitmapDrawableFromView.getInstance(context);
        // JSONArray kitchenList = response.getJSONArray("hotels");

        Marker latestMarker = null;
        LatLng latestLatLong = null;
        JSONObject latestKitchen = null;
        Marker mapMarker = googleMap.addMarker(new MarkerOptions()
                        .position(latLang)
                        .icon(
                                BitmapDescriptorFactory.defaultMarker(hueBlue))
        );

//                latestKitchen = kitchen;
        latestMarker = mapMarker;
        latestLatLong = latLang;

        // Move the camera instantly to hamburg with a zoom of 15.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latestLatLong, 15));

        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        //final TextView restaurantName = (TextView) getActivity().findViewById(R.id.restaurant_map_name_info);
        //restaurantName.setText(latestKitchen.getString("displayName"));
        //final TextView restaurantaddress = (TextView) getActivity().findViewById(R.id.restaurant_map_addr_info);
        //restaurantaddress.setText(latestKitchen.getString("address"));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker selectedMarker) {
                //  restaurantName.setText(selectedMarker.getTitle());
                // restaurantaddress.setText(selectedMarker.getSnippet());
                return true;
            }
        });
    }
}
