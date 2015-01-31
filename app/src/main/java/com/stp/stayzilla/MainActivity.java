package com.stp.stayzilla;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addHomeFragment();
    }

    private void addHomeFragment() {

        KitchensMapFragment homeFragment = new KitchensMapFragment();
            Bundle args = new Bundle();
            homeFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, homeFragment,KitchensMapFragment.class.getName())
                    .commit();
        }


}
