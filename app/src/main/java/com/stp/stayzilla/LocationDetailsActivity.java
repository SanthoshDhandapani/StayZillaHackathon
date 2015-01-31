package com.stp.stayzilla;

import android.app.Activity;
import android.os.Bundle;


public class LocationDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LocationDetailFragment())
                    .commit();
        }
    }





}
