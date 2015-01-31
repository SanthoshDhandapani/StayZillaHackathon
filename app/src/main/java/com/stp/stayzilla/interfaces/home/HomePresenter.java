package com.stp.stayzilla.interfaces.home;

import com.squareup.otto.Subscribe;

import com.stp.stayzilla.events.LoadColorsTabsDisk;
import com.stp.stayzilla.events.LoadTitleTabsDisk;

/**
 * Created by Halyson on 20/01/15.
 */
public interface HomePresenter {
    @Subscribe
    void onDestroy();
}
