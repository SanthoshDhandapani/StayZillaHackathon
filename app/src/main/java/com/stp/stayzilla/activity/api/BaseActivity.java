/*
 * Copyright (C) 2014 Antonio Leiva Gordillo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stp.stayzilla.activity.api;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.stp.stayzilla.KitchensMapFragment;
import com.stp.stayzilla.R;
import com.stp.stayzilla.activity.HelperActivity;
import com.stp.stayzilla.activity.HomeActivity;
import com.stp.stayzilla.constants.AppConstants;
import com.stp.stayzilla.constants.DrawerMenu;
import com.stp.stayzilla.fragment.HomeFragment;
import com.stp.stayzilla.fragment.NavigationDrawerFragment;
import com.stp.stayzilla.utility.PrintFontIconDrawable;

import org.json.JSONArray;

public abstract class BaseActivity extends ActionBarActivity implements
                                    NavigationDrawerFragment.NavigationDrawerCallbacks {
    protected Toolbar mToolBar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    public JSONArray hotelEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceIdentifier());
        Bundle b = getIntent().getExtras();
        String hotelEntriesData=b.getString(AppConstants.RESPONSE_KEY);
        try {
            this.hotelEntries = new JSONArray(hotelEntriesData);
        } catch (Exception e) {e.printStackTrace();}

        loadViewComponents();
        loadInfoToolbar();
        loadInfoDrawerMenu();
    }

    private void loadViewComponents() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.screen_default_navigation_drawer);
        mToolBar = (Toolbar) findViewById(R.id.screen_default_toolbar);
    }


    private void loadInfoToolbar() {
        setSupportActionBar(mToolBar);
      //  getSupportActionBar().setTitle(getTitleToolBar());
        TextView appText = (TextView) findViewById(R.id.app_name);
        appText.setText(getTitleToolBar());
        ImageView searchView = (ImageView) findViewById(R.id.search_view);
        searchView.setImageDrawable(PrintFontIconDrawable.getInstance(this)
        .getDrawableFontIcon(R.string.fa_search,android.R.color.white,
                R.dimen.action_bar_icon_size));
    }


    private void loadInfoDrawerMenu() {
        mNavigationDrawerFragment.setUp(
                R.id.screen_default_navigation_drawer,
                (DrawerLayout) findViewById(R.id.screen_default_drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    }

    public void fragmentTransaction(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.screen_default_container, fragment)
                    .commit();
        }
    }

    protected abstract int setLayoutResourceIdentifier();

    protected abstract int getTitleToolBar();
}
