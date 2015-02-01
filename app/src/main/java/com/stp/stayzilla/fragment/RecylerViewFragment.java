package com.stp.stayzilla.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import com.stp.stayzilla.MainActivity;
import com.stp.stayzilla.R;
import com.stp.stayzilla.adapter.RecyclerViewCardsAdapter;
import com.stp.stayzilla.fragment.api.BaseFragment;
import com.stp.stayzilla.model.CardViewBean;
import com.stp.stayzilla.utility.PrintFontIconDrawable;
import com.yalantis.pulltorefresh.library.PullToRefreshView;

import org.json.JSONArray;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RecylerViewFragment extends BaseFragment {

    private static final String MOCK_URL = "http://lorempixel.com/800/400/nightlife/";
    private View mViewRecyclerCardsView;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private JSONArray hotelEntries;
    @InjectView(R.id.mapview) com.melnykov.fab.FloatingActionButton mapFab;
    @InjectView(R.id.filter_fab) com.melnykov.fab.FloatingActionButton filterFab;
    private final int REFRESH_DELAY = 2000;
    private int MAX_RADIUS = 100;

    @InjectView(R.id.hotels_refresh_view)
    PullToRefreshView mPullToRefreshView;

    public static RecylerViewFragment newInstance(JSONArray hotelEntries) {
        RecylerViewFragment recylerViewFragment = new RecylerViewFragment();
        recylerViewFragment.hotelEntries = hotelEntries;
        return recylerViewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewRecyclerCardsView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.inject(this, mViewRecyclerCardsView);

        loadViewComponents();
        loadInfoView();
        mapFab.setImageDrawable(PrintFontIconDrawable.getInstance(getActivity())
                .getDrawableFontIcon(R.string.fa_location_arrow, android.R.color.white,
                        R.dimen.fab_icon_size));
        filterFab.setImageDrawable(PrintFontIconDrawable.getInstance(getActivity())
                .getDrawableFontIcon(R.string.fa_filter, android.R.color.white,
                        R.dimen.fab_icon_size));

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });

        return mViewRecyclerCardsView;
    }

    @OnClick(R.id.mapview)
    public void onMapFabClick(View view) {
    }

    private void loadViewComponents() {
        mRecyclerView = (RecyclerView) mViewRecyclerCardsView.findViewById(R.id.fragment_recyler_view_content_main);
    }

    private void loadInfoView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewCardsAdapter(getActivity(),hotelEntries));

    }

    private List<CardViewBean> createMockList() {
        List<CardViewBean> listCard = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listCard.add(new CardViewBean(MOCK_URL + i));
        }
        return listCard;
    }
}

