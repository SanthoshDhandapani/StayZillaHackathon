package com.stp.stayzilla.fragment;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stp.stayzilla.R;
import com.stp.stayzilla.activity.HomeActivity;
import com.stp.stayzilla.activity.api.BaseActivity;
import com.stp.stayzilla.adapter.HomePagerAdapter;
import com.stp.stayzilla.fragment.api.BaseFragment;
import com.stp.stayzilla.interfaces.home.HomeView;
import com.stp.stayzilla.presenter.HomePresenterImpl;

import org.json.JSONArray;
import org.json.JSONObject;


public class HomeFragment extends BaseFragment implements HomeView {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private View mViewHome;
    private ViewPager mViewPager;
    private HomePresenterImpl mHomePresenter;
    JSONArray hotels = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewHome = inflater.inflate(R.layout.fragment_default, container, false);
        this.hotels = ((HomeActivity) getActivity()).hotels;
        loadViewComponents();
        initPresenter();
        return mViewHome;
    }

    @Override
    public void onDestroy() {
        mHomePresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void loadViewComponents() {
        mViewPager = (ViewPager) mViewHome.findViewById(R.id.fragment_home_view_pager);
        mViewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(),hotels));
    }

    @Override
    public void initPresenter() {
        mHomePresenter = new HomePresenterImpl(this);
    }
}