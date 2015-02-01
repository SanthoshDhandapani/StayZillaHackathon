package com.stp.stayzilla.fragment;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.stp.stayzilla.R;
import com.stp.stayzilla.adapter.RecyclerViewCardsAdapter;
import com.stp.stayzilla.constants.FragmentNames;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import se.walkercrou.places.Place;


/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class LocationDetailFragment extends Fragment {

    @InjectView(R.id.locationimage)
    ImageView mLocationimage;
    @InjectView(R.id.locationName)
    TextView locationName;
    @InjectView(R.id.checkBox)
    CheckBox mCheckBox;
    @InjectView(R.id.startDate)
    Button mStartDate;
    @InjectView(R.id.endDate)
    Button mEndDate;
    @InjectView(R.id.separator)
    ImageView mSeparator;

    private RecyclerView mRecyclerView;

    public static Place selectedPlace;
    String name;
    double lati;
    double lan;

    public LocationDetailFragment() {
        // Required empty public constructor
    }

    public static LocationDetailFragment newInstance(Place place){
        LocationDetailFragment fragment = new LocationDetailFragment();
        //fragment.selectedPlace=place;

        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        name = getArguments().getString("name");
        lati = getArguments().getDouble("lat");
        lan = getArguments().getDouble("lan");
        ButterKnife.inject(this, view);
        initView();
         mRecyclerView = (RecyclerView) view.findViewById(R.id.hotellist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        return view;
    }

    private void initView() {
        Picasso.with(getActivity()).load("http://www.ultimatebali.com/sites/default/files/styles/listing_slideshow/public/TirtaNilaPreview_35_NightLightsOnTheBay_1.jpg").fit().into(mLocationimage);
        locationName.setText(name);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://180.92.168.7/hotels";

        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                processResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("checkin",mStartDate.getText().toString());
                params.put("checkout",mEndDate.getText().toString());
                params.put("property_type","Hotels");
                params.put("lat",String.valueOf(lati));
                params.put("lng",String.valueOf(lan));

//                params.put("user",userAccount.getUsername());
//                params.put("pass",userAccount.getPassword());


                return params;
            }
        };
        queue.add(request);


    }

    private void processResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            JSONArray hotels  = json.getJSONArray("hotels");

            mRecyclerView.setAdapter(new RecyclerViewCardsAdapter(getActivity(),hotels));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.startDate)
    public void onStartClicked(){
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                formatAndSet(mStartDate,year,monthOfYear,dayOfMonth);
            }
        },2015,02,03);
        dialog.show();
    }

    @OnClick(R.id.endDate)
    public void onEndClicked(){
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                formatAndSet(mEndDate,year,monthOfYear,dayOfMonth);

            }
        },2015,02,20);
        dialog.show();
    }

    private void formatAndSet(Button mEndDate, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/mm/yyyy");
        mEndDate.setText(dateFormat.format(calendar.getTime()));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
