package com.stp.stayzilla.interfaces.home;

import java.util.List;

public interface HomeRepository {
    void recoverTitleTabs();

    void recoverColorsTabs();

    int getColorTab();

    int getDividerColorTab();

    int getIndicatorColorTab();

    void onDestroy();


}
