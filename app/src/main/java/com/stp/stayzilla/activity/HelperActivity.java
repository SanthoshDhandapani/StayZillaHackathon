package com.stp.stayzilla.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.stp.stayzilla.KitchensMapFragment;
import com.stp.stayzilla.R;
import com.stp.stayzilla.constants.AppConstants;
import com.stp.stayzilla.constants.DrawerMenu;

/**
 * Created by Santhosh on 01-02-2015.
 */
public class HelperActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.helper_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        if(b!=null) {
            int hotelEntriesData = b.getInt(AppConstants.RESPONSE_KEY);

            switch (hotelEntriesData) {

                case DrawerMenu.FRAGMENT_WISH_LIST:
                    getSupportFragmentManager().beginTransaction().add(R.id.container,
                            new KitchensMapFragment(), KitchensMapFragment.class.getName()).commit();
                    break;
                case DrawerMenu.FRAGMENT_FRIENDS:
                    getSupportFragmentManager().beginTransaction().add(R.id.container,
                            new KitchensMapFragment(), KitchensMapFragment.class.getName()).commit();
                    break;
                case DrawerMenu.FRAGMENT_ACCOUNT:
                    getSupportFragmentManager().beginTransaction().add(R.id.container,
                            new KitchensMapFragment(), KitchensMapFragment.class.getName()).commit();
                    break;

                case DrawerMenu.FRAGMENT_BOOKINGS:
                    getSupportFragmentManager().beginTransaction().add(R.id.container,
                            new KitchensMapFragment(), KitchensMapFragment.class.getName()).commit();
                    break;
            }
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.container,
                    new KitchensMapFragment(), KitchensMapFragment.class.getName()).commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
