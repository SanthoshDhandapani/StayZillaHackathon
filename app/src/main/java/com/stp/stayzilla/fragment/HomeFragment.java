package com.stp.stayzilla.fragment;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        getResponse();
        initPresenter();
        return mViewHome;
    }

    void processResponse(String response){
      //  System.out.println("Response is "+response);

        try {
            JSONObject reader = new JSONObject(response);
            hotels  = reader.getJSONArray("hotels");
            loadViewComponents();
            initPresenter();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getResponse() {
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
                params.put("checkin","02/02/2015");
                params.put("checkout","04/02/2015");
                params.put("property_type","Hotels");
                params.put("lat","11.40");
                params.put("lng","76.40");

//                params.put("user",userAccount.getUsername());
//                params.put("pass",userAccount.getPassword());


                return params;
            }
        };
        queue.add(request);
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