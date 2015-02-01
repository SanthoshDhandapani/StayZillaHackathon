package com.stp.stayzilla.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.Session;
import com.stp.stayzilla.KitchensMapFragment;
import com.stp.stayzilla.R;
import com.stp.stayzilla.activity.api.BaseActivity;
import com.stp.stayzilla.constants.AppConstants;
import com.stp.stayzilla.constants.FragmentNames;
import com.stp.stayzilla.fragment.HomeFragment;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            HomeFragment fragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.screen_default_container, fragment, FragmentNames.FRAGMENT_HOME_).commit();

            ImageView searchImageView = (ImageView) findViewById(R.id.search_view);
            searchImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this,HelperActivity.class);
                    startActivity(intent);
                }
            });
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

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        super.onNavigationDrawerItemSelected(position);

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.RESPONSE_KEY,position);
        Intent intent = new Intent(HomeActivity.this, HelperActivity.class);
        startActivity(intent);
    }
}
