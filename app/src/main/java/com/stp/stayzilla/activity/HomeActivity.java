package com.stp.stayzilla.activity;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.Session;
import com.stp.stayzilla.R;
import com.stp.stayzilla.activity.api.BaseActivity;
import com.stp.stayzilla.constants.FragmentNames;
import com.stp.stayzilla.fragment.HomeFragment;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            HomeFragment fragment = new HomeFragment();
            fragment.setHasOptionsMenu(true);
            getSupportFragmentManager().beginTransaction().add(R.id.screen_default_container, fragment, FragmentNames.FRAGMENT_HOME_).commit();
        }
    }

    @Override
    protected int setLayoutResourceIdentifier() {
        return R.layout.screen_default;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_name;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }


}
