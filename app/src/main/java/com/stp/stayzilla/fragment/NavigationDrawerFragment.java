package com.stp.stayzilla.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.stp.stayzilla.R;
import com.stp.stayzilla.adapter.DrawerMenuAdapter;
import com.stp.stayzilla.fragment.api.BaseFragment;
import com.stp.stayzilla.model.DrawerMenuBean;


/**
 * Fragment utilizado para o gerenciamento de interações para e apresentação do menu drawer.
 */
public class
        NavigationDrawerFragment extends BaseFragment {
    private static final String TAG = NavigationDrawerFragment.class.getSimpleName();

    /**
     * Lembra a posição do item selecionado.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Por as diretrizes de design, você deve mostrar o menu drawer até que o usuário expande ele manualmente.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Componente
     */
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View drawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private TextView mProfileName;
    private ProfilePictureView mProfileImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        drawerLayout =  inflater.inflate(R.layout.fragment_drawer_menu, container, false);
        mDrawerListView = (ListView)drawerLayout.findViewById(R.id.fragment_drawerMenu_listView);
        drawerLayout.findViewById(R.id.profile_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLogin();
            }
        });
        mProfileName= (TextView)drawerLayout.findViewById(R.id.profile_name);
        mProfileImage =(ProfilePictureView)drawerLayout.findViewById(R.id.profile_image);

        loadListeners();
        loadInfoView();

        return drawerLayout;
    }

    private void fbLogin() {

            Session.openActiveSession(getActivity(), true, new Session.StatusCallback() {
                @Override
                public void call(Session session, SessionState state, Exception exception) {
                    if (exception != null) {
                        Log.d("Facebook", exception.getMessage());
                    }
                    Log.d("Facebook", "Session State: " + session.getState());
                    // you can make request to the /me API or do other stuff like post, etc. here
                    if(session.isOpened()) {
                        Request.newMeRequest(session, new Request.GraphUserCallback() {
                            @Override
                            public void onCompleted(GraphUser user, Response response) {
                                if (user != null) {
                                    try {
                                        mProfileImage.setProfileId(user.getId());
                                        mProfileName.setText(user.getName());
//                                    URL imgUrl = new URL("http://graph.facebook.com/"
//                                            + user.getId() + "/picture?type=large");
//
//                                    InputStream in = (InputStream) imgUrl.getContent();
//                                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                                        //Bitmap bitmap = BitmapFactory.decodeStream(imgUrl      // tried this also
                                        //.openConnection().getInputStream());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).executeAsync();
                    }
                }
            });


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout != null) {
                if (isDrawerOpen()) {
                    mDrawerLayout.closeDrawer(mFragmentContainerView);
                } else {
                    mDrawerLayout.openDrawer(mFragmentContainerView);
                }
            } else {
                getActivity().finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadListeners() {
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }

    private void loadInfoView() {
        ArrayList<DrawerMenuBean> menuDrawerListItens = loadMenuDrawerItens();
        if (menuDrawerListItens != null) {
            DrawerMenuAdapter drawerMenuAdapter = new DrawerMenuAdapter(mContext, menuDrawerListItens);

            mDrawerListView.setAdapter(drawerMenuAdapter);
            mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        }
    }

    private ArrayList<DrawerMenuBean> loadMenuDrawerItens() {
        String[] menuDrawerTitleArray, menuDrawerIconsArray;
        ArrayList<DrawerMenuBean> menuDrawerListItems = null;

        try {
            menuDrawerListItems = new ArrayList<DrawerMenuBean>();
            menuDrawerTitleArray = getActivity().getResources().getStringArray(R.array.fragment_drawerMenu_title);
            menuDrawerIconsArray = getActivity().getResources().getStringArray(R.array.fragment_drawerMenu_icons);

            for (int index=0; index<menuDrawerTitleArray.length; index++) {
                DrawerMenuBean drawerMenuBean = new DrawerMenuBean(menuDrawerTitleArray[index]);
                drawerMenuBean.setFontName(menuDrawerIconsArray[index]);
                menuDrawerListItems.add( drawerMenuBean );
            }

            return menuDrawerListItems;
        } catch (Resources.NotFoundException notFoundExcepetion) {
            Log.e(TAG, "Error Getting The Array", notFoundExcepetion);
        }
        return menuDrawerListItems;
    }


    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.ic_drawer_menu_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
