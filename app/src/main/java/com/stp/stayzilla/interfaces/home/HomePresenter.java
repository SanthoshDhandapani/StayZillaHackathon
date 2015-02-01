package com.stp.stayzilla.interfaces.home;

import com.squareup.otto.Subscribe;

import com.stp.stayzilla.events.LoadColorsTabsDisk;
import com.stp.stayzilla.events.LoadTitleTabsDisk;

public interface HomePresenter {
    @Subscribe
    void onDestroy();
}
