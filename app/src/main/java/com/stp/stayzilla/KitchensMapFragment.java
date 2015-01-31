package com.stp.stayzilla;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

/**
 * Created by santhosh on 12/22/14.
 */
public class KitchensMapFragment extends SupportMapFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    static final LatLng HAMBURG = new LatLng(12.979432, 80.226490);
    static final LatLng KIEL = new LatLng(12.977790, 80.224465);
    MapFragment kitchenMiniMapFragment = new MapFragment();
    Bundle bundle = new Bundle();
    JSONObject response = new JSONObject();
    ProgressDialog dialog;
    SlidingUpPanelLayout mLayout;
    View rootView;
    TextView searchedPlace;
    LinearLayout searchedPlaceLayout;
    private GooglePlaces client;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Place selectedPlace;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        super.onCreateView(inflater, container, args);
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        this.rootView = rootView;
        initView();
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        //dialog.show();
        initMap(null);
        //getSearchTask.execute();

        return rootView;
    }

    private void initMap(ArrayList<LatLng> latLng1) {
        ArrayList<LatLng> latLng = getStaticNearByPlaces();
        bundle.putParcelableArrayList("latlng", latLng);
        kitchenMiniMapFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.map, kitchenMiniMapFragment).commit();
    }

    private void initView() {
        searchedPlace = (TextView) rootView.findViewById(R.id.searched_Place);
        searchedPlaceLayout = (LinearLayout)rootView.findViewById(R.id.searched_Place_Layout);
        searchedPlaceLayout.setOnClickListener(this);
        AutoCompleteTextView autoCompView = (AutoCompleteTextView) rootView.findViewById(R.id.auto_CompleteTextView);
        autoCompView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1));
        autoCompView.setOnItemClickListener(this);
        autoCompView.setVisibility(View.GONE);
    }

//    public void createSlipeUpPanel(){
//        mLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
//        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
//            @Override
//            public void onPanelSlide(View panel, float slideOffset) {
//            }
//
//
//            @Override
//            public void onPanelExpanded(View panel) {
//
//
//            }
//
//
//            @Override
//            public void onPanelCollapsed(View panel) {
//
//
//            }
//
//
//            @Override
//            public void onPanelAnchored(View panel) {
//            }
//
//
//            @Override
//            public void onPanelHidden(View panel) {
//            }
//        });
//    }

    AsyncTask getSearchTask = new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] params) {
            HashMap<String, String> postParams = new HashMap<String, String>();
            postParams.put("location", "Chennai");
            postParams.put("checkin", "02/02/2015");
            postParams.put("checkout", "05/02/2015");
            postParams.put("property_type", "Hotels");
            response = getSearchResponse(postParams);
            try {
                System.out.println("pradeep " + response.getJSONArray("hotels").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Object o) {
            dialog.dismiss();
            bundle.putString("response", response.toString());
            kitchenMiniMapFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.map, kitchenMiniMapFragment).commit();
            //initMap();
            // createSlipeUpPanel();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * <p/>
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = getMap();
//            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap();
//            }
//        }
        if (client == null)
            client = new GooglePlaces(API_KEY);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(HAMBURG).title("Marker"));
    }


    private JSONObject getSearchResponse(HashMap<String, String> postParams) {
        try {
            return HTTPClient.executeHttpPost(postParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }



    private static final String LOG_TAG = "ExampleApp";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyDW9lkfp8wT52-Lj6cH1Xluc2g1m3GkUfo";

    private ArrayList<PlaceInfo> autocomplete(String input) {
        ArrayList<PlaceInfo> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:in");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            System.out.println("REsults: " + jsonObj);
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<PlaceInfo>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
//                resultList.add(Place.parseDetails(client, predsJsonArray.getJSONObject(i).toString()).getName());
                JSONObject placeObj = predsJsonArray.getJSONObject(i);
                PlaceInfo info = new PlaceInfo();
                info.description = placeObj.getString("description");
                info.id = placeObj.getString("place_id");
                resultList.add(info);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    public ArrayList<String> newAutoComplete(String input) {

        List<Place> places = client.getPlacesByQuery(input, GooglePlaces.MAXIMUM_RESULTS);
        ArrayList<String> nameList = new ArrayList<String>(places.size());
        for (Place place : places) {
            nameList.add(place.getName());

        }

        return nameList;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PlaceInfo placeInfo = ((PlacesAutoCompleteAdapter) parent.getAdapter()).getPlaceInfo(position);
        fetchPlaceDetails(placeInfo);
    }

    private void fetchPlaceDetails(PlaceInfo placeInfo) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://maps.googleapis.com/maps/api/place/details/json?key=" + API_KEY + "&placeid=" + placeInfo.id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                selectedPlace = Place.parseDetails(client, response);
//                kitchenMiniMapFragment.addLatLng(new LatLng(selectedPlace.getLatitude(), selectedPlace.getLongitude()));

                markPlace();
                System.out.println(selectedPlace.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
//        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet();
//        BasicHttpParams params = new BasicHttpParams();
//        params.setParameter("key",API_KEY).setParameter("placeid",placeInfo.id);
//        httpGet.setParams(params);
//        try {
//           HttpEntity entity= defaultHttpClient.execute(httpGet).getEntity();
//            EntityUtils.toString(entity);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }




    private void markPlace() {
        searchedPlace.setText(selectedPlace.getName());
        System.out.println("pradeep mark place called");
        //initMap(new LatLng(selectedPlace.getLatitude(), selectedPlace.getLongitude()));
        kitchenMiniMapFragment.drawMarkerInMap(new LatLng(selectedPlace.getLatitude(), selectedPlace.getLongitude()), BitmapDescriptorFactory.HUE_BLUE);
    }

    @Override
    public void onClick(View v) {
        if(v == searchedPlaceLayout){
            navigateToDetailsScreen();
        }
    }

    private void navigateToDetailsScreen() {
        LocationDetailFragment.selectedPlace=selectedPlace;
        startActivity(new Intent(getActivity(),LocationDetailsActivity.class));

    }

    public ArrayList<LatLng> getStaticBeaches(){
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        list.add(new LatLng(Double.parseDouble("15.312646"), Double.parseDouble("73.907753")));
        list.add(new LatLng(Double.parseDouble("15.312646"), Double.parseDouble("73.907753")));
        list.add(new LatLng(Double.parseDouble("8.086614"), Double.parseDouble("77.554441")));
        list.add(new LatLng(Double.parseDouble("10.913273"), Double.parseDouble("79.848172")));
        list.add(new LatLng(Double.parseDouble("9.956001"), Double.parseDouble("76.240253")));
        list.add(new LatLng(Double.parseDouble("12.909202"), Double.parseDouble("74.869080")));
        list.add(new LatLng(Double.parseDouble("17.697343"), Double.parseDouble("83.191223")));
        list.add(new LatLng(Double.parseDouble("21.867553"), Double.parseDouble("88.343811")));
        return list;
    }

    public ArrayList<LatLng> getStaticTourPlaces(){
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        list.add(new LatLng(Double.parseDouble("12.492225"), Double.parseDouble("76.690063")));
        list.add(new LatLng(Double.parseDouble("9.960059"), Double.parseDouble("78.195190")));
        list.add(new LatLng(Double.parseDouble("15.224564"), Double.parseDouble("74.102783")));
        list.add(new LatLng(Double.parseDouble("27.160586"), Double.parseDouble("78.013916")));
        list.add(new LatLng(Double.parseDouble("34.128858"), Double.parseDouble("76.343994")));
        list.add(new LatLng(Double.parseDouble("11.410538"), Double.parseDouble("76.716156")));
        return list;
    }

    public ArrayList<LatLng> getStaticNearByPlaces(){
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        list.add(new LatLng(Double.parseDouble("13.082680"), Double.parseDouble("80.270718")));
        list.add(new LatLng(Double.parseDouble("13.083841"), Double.parseDouble("80.260792")));
        list.add(new LatLng(Double.parseDouble("13.081641"), Double.parseDouble("80.251792")));
        list.add(new LatLng(Double.parseDouble("13.082441"), Double.parseDouble("80.254792")));
        list.add(new LatLng(Double.parseDouble("13.084841"), Double.parseDouble("80.261792")));
        list.add(new LatLng(Double.parseDouble("13.083841"), Double.parseDouble("80.251792")));
        return list;
    }

    class PlaceInfo {
        String id;
        String description;
    }


    private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<PlaceInfo> resultList;

        public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index).description;
        }

        public PlaceInfo getPlaceInfo(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

}
