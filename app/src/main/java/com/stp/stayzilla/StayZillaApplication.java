package com.stp.stayzilla;

import android.app.Application;
import android.content.Context;

public class StayZillaApplication extends Application {
    private static StayZillaApplication mStayZillaApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mStayZillaApplication = this;
    }


    public static StayZillaApplication getApplication() {
        return mStayZillaApplication;
    }

    public static Context getApplicationCtx() {
        return mStayZillaApplication.getApplicationContext();
    }
}
