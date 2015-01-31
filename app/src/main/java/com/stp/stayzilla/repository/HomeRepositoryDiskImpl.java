package com.stp.stayzilla.repository;

import android.content.res.Resources;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import com.stp.stayzilla.StayZillaApplication;
import com.stp.stayzilla.R;
import com.stp.stayzilla.events.LoadColorsTabsDisk;
import com.stp.stayzilla.events.LoadTitleTabsDisk;
import com.stp.stayzilla.interfaces.home.HomeRepository;
import com.stp.stayzilla.utility.BusProvider;

public class HomeRepositoryDiskImpl implements HomeRepository {
    private static final String TAG = HomeRepositoryDiskImpl.class.getSimpleName();

    public HomeRepositoryDiskImpl() {
        BusProvider.getInstance().register(this);
    }

    @Override
    public void recoverTitleTabs() {
        try {
            List<String> listTitleTabs = Arrays.asList(StayZillaApplication.getApplicationCtx().getResources().getStringArray(R.array.fragment_home_sections_tabs_title));

            BusProvider.getInstance().post(new LoadTitleTabsDisk(listTitleTabs));
        } catch (Resources.NotFoundException notFoundExcepetion) {
            Log.e(TAG, "Error Getting The Array", notFoundExcepetion);
        }
    }

    @Override
    public void recoverColorsTabs() {
        BusProvider.getInstance().post(new LoadColorsTabsDisk(getColorTab(), getDividerColorTab(), getIndicatorColorTab()));
    }

    @Override
    public int getColorTab() {
        return StayZillaApplication.getApplicationCtx().getResources().getColor(android.R.color.white);

    }

    @Override
    public int getDividerColorTab() {
        return StayZillaApplication.getApplicationCtx().getResources().getColor(R.color.theme_dialer_primary);
    }

    @Override
    public int getIndicatorColorTab() {
        return StayZillaApplication.getApplicationCtx().getResources().getColor(android.R.color.transparent);
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
    }
}
