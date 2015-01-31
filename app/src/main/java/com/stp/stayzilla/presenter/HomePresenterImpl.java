package com.stp.stayzilla.presenter;

import com.squareup.otto.Subscribe;

import com.stp.stayzilla.events.LoadColorsTabsDisk;
import com.stp.stayzilla.events.LoadTitleTabsDisk;
import com.stp.stayzilla.interfaces.home.HomePresenter;
import com.stp.stayzilla.interfaces.home.HomeView;
import com.stp.stayzilla.service.HomeServiceImpl;
import com.stp.stayzilla.utility.BusProvider;

/**
 * Created by Halyson on 20/01/15.
 */
public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = HomePresenterImpl.class.getSimpleName();
    private HomeView mHomeView;
    private HomeServiceImpl mHomeService;

    public HomePresenterImpl(HomeView homeView) {
        BusProvider.getInstance().register(this);
        mHomeView = homeView;
        mHomeService = new HomeServiceImpl();
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
        mHomeService.onDestroy();
        mHomeService = null;
    }
}
