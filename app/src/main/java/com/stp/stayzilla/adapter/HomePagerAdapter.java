package com.stp.stayzilla.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import com.stp.stayzilla.fragment.RecylerViewFragment;
import com.stp.stayzilla.fragment.DefaultFragment;
import com.stp.stayzilla.fragment.Fragment2;
import com.stp.stayzilla.fragment.Fragment3;

import org.json.JSONArray;

public class HomePagerAdapter extends FragmentPagerAdapter {

    JSONArray hotelEntries;
    public HomePagerAdapter(FragmentManager childFragmentManager, JSONArray hotelEntries) {
        super(childFragmentManager );
        this.hotelEntries = hotelEntries;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("Loading "+position);
        switch (position) {
            case 0:
                return RecylerViewFragment.newInstance(hotelEntries);
            default:
                return DefaultFragment.newInstance();
        }
    }
}
