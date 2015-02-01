package com.stp.stayzilla;

import android.app.Application;
import android.content.Context;

import com.github.johnkil.print.PrintConfig;

public class StayZillaApplication extends Application {
    private static StayZillaApplication mStayZillaApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        PrintConfig.initDefault(getAssets(), getString(R.string.default_typeface));
        mStayZillaApplication = this;
    }


    public static StayZillaApplication getApplication() {
        return mStayZillaApplication;
    }

    public static Context getApplicationCtx() {
        return mStayZillaApplication.getApplicationContext();
    }
}
