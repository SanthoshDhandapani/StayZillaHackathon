package com.stp.stayzilla.activity;

import android.os.Bundle;

import com.stp.stayzilla.R;
import com.stp.stayzilla.activity.api.BaseActivity;
import com.stp.stayzilla.constants.FragmentNames;
import com.stp.stayzilla.fragment.HomeFragment;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.screen_default_container, new HomeFragment(), FragmentNames.FRAGMENT_HOME_).commit();
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

}
