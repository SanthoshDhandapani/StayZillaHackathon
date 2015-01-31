package com.stp.stayzilla;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by santhosh on 12/10/14.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View rootView =  inflater.inflate(getLayoutId(), container, false);
        ButterKnife.inject(this,rootView);
        rootView = initView(rootView,savedInstanceState);

        return rootView;
    }

    protected abstract View initView(View rootView,Bundle savedInstanceState);

    public abstract int getLayoutId();

}
