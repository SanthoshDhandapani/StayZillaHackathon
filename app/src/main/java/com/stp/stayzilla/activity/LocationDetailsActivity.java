package com.stp.stayzilla.activity;

import android.app.Activity;
import android.os.Bundle;

import com.stp.stayzilla.fragment.LocationDetailFragment;
import com.stp.stayzilla.R;


public class LocationDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        if (savedInstanceState == null) {
            getIntent().getBundleExtra("place");
            LocationDetailFragment frag = new LocationDetailFragment();
            frag.setArguments(getIntent().getBundleExtra("place"));
            getFragmentManager().beginTransaction()
                    .add(R.id.container, frag)
                    .commit();
        }
    }





}
