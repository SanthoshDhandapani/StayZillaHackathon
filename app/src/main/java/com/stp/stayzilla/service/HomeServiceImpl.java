package com.stp.stayzilla.service;

import com.stp.stayzilla.interfaces.home.HomeService;
import com.stp.stayzilla.repository.HomeRepositoryDiskImpl;

/**
 * Created by Halyson on 20/01/15.
 */
public class HomeServiceImpl implements HomeService {
    private static final String TAG = HomeServiceImpl.class.getSimpleName();
    private HomeRepositoryDiskImpl mHomeRepositoryDisk;

    public HomeServiceImpl() {
        mHomeRepositoryDisk = new HomeRepositoryDiskImpl();
    }

    @Override
    public void recoverTitleTabs() {
        mHomeRepositoryDisk.recoverTitleTabs();
    }

    @Override
    public void onDestroy() {
        mHomeRepositoryDisk.onDestroy();
        mHomeRepositoryDisk = null;
    }
}
