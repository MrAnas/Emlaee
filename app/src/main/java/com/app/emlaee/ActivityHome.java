package com.app.emlaee;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.emlaee.fragments.AboutUsFragment;
import com.app.emlaee.fragments.LevelsFragment;
import com.app.emlaee.fragments.PerformanceFragment;
import com.app.emlaee.fragments.SettingsFragment;
import com.app.emlaee.interfaces.OnItemClickListener;
import com.app.emlaee.utils.Constants;

public class ActivityHome extends AppCompatActivity implements OnItemClickListener, LevelsFragment.OnFragmentInteractionListener, PerformanceFragment.OnFragmentInteractionListener {
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View view, int position) {

    }

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ImageView mDrawerIcon;
    private ImageView mBackBtn;
    public static TextView toolbar_title;
    private Dialog mHintDialog;
    private LinearLayout mLayoutLevels;
    private LinearLayout mLayoutPerformance;
    private int test;
    View viewLine;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    FragmentManager fragmentManager;
    TextView txtHome, txtSetting, txtAboutUs;
    LinearLayout layoutGetHint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        /***initialize ***/
        fragmentManager = getSupportFragmentManager();

        setSharedPrefrences();

        FindViewById();

    }

    private void FindViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        viewLine = (View)findViewById(R.id.viewLine);
        setSupportActionBar(toolbar);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        mDrawerIcon = (ImageView) findViewById(R.id.menu_drawer);
        mBackBtn = (ImageView) findViewById(R.id.back_ic);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLayoutLevels = (LinearLayout) findViewById(R.id.layout_levels);
        mLayoutPerformance = (LinearLayout) findViewById(R.id.layout_performance);

        layoutGetHint = (LinearLayout)findViewById(R.id.layoutGetHint);

        showBottomButton();

         /*Set Navigation */
        setNavigationView();

        /******Current Fragment On Activity*****/
//        replaceWithCategoryFragment();
        mLayoutLevels.setBackgroundResource(R.color.gray);
        mLayoutPerformance.setBackgroundResource(R.color.black);
        replaceWithLevelFragemnt();

        SetClickListener();
    }

    private void setNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        txtHome = (TextView) navigationView.findViewById(R.id.txtHome);
        txtSetting = (TextView) navigationView.findViewById(R.id.txtSetting);
        txtAboutUs = (TextView) navigationView.findViewById(R.id.txtAboutUs);

        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //*******Replace With Categories Fragment*******//*
                showBottomButton();
                mLayoutLevels.setBackgroundResource(R.color.gray);
                mLayoutPerformance.setBackgroundResource(R.color.black);
                replaceWithLevelFragemnt();
                if (drawer != null && drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });
        txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //*****BottomButton Status******//*
                hideBottomButton();
                setToolbarText("");
                SettingsFragment mSettingsFragment = new SettingsFragment();
                Bundle bundle = new Bundle();
                switchFragment(mSettingsFragment, Constants.SETTINGS_FRAGMENT, bundle);
                if (drawer != null && drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });
        txtAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //*****BottomButton Status******//*
                hideBottomButton();
                setToolbarText("");
                AboutUsFragment mAboutUsFragment = new AboutUsFragment();
                Bundle bundle = new Bundle();
                switchFragment(mAboutUsFragment, Constants.ABOUTUS_FRAGMENT, bundle);
                if (drawer != null && drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });
    }

    public static void setToolbarText(String strText) {
        toolbar_title.setText(strText);
    }

    private void SetClickListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer != null && drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    finish();
                }

            }
        });

        mDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer != null && !drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        mLayoutLevels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mLayoutLevels.setBackgroundResource(R.color.gray);
//                mLayoutPerformance.setBackgroundResource(R.color.black);
                replaceWithLevelFragemnt();
            }
        });

        mLayoutPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mLayoutPerformance.setBackgroundResource(R.color.gray);
//                mLayoutLevels.setBackgroundResource(R.color.black);
                replaceWithPerformanceFragment();
            }
        });

        mLayoutLevels.performClick();

    }


    @Override
    public void onBackPressed() {

        if (drawer != null && drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }



    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_category) {

            *//*******Replace With Categories Fragment*******//*
            replaceWithCategoryFragment();

        } else if (id == R.id.nav_settings) {
//            *//*****BottomButton Status******//*
            hideBottomButton();
            setToolbarText(getResources().getString(R.string.nav_setting));
            SettingsFragment mSettingsFragment = new SettingsFragment();
            Bundle bundle = new Bundle();
            switchFragment(mSettingsFragment, Constants.SETTINGS_FRAGMENT, bundle);
        } else if (id == R.id.nav_aboutus) {
//            */

    /*****
     * BottomButton Status
     ******//*
            hideBottomButton();
            setToolbarText("");
            AboutUsFragment mAboutUsFragment = new AboutUsFragment();
            Bundle bundle = new Bundle();
            switchFragment(mAboutUsFragment, Constants.ABOUTUS_FRAGMENT, bundle);
        }


        if (drawer != null && drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
        return true;
    }*/
    private void setSharedPrefrences() {
        mPref = this.getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        mEditor = mPref.edit();
    }


    public void switchFragment(final Fragment fragment, final String Tag, final Bundle bundle) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    if (fragment != null) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment, Tag);
                        fragmentTransaction.addToBackStack(Tag);
                        if (bundle != null)
                            fragment.setArguments(bundle);
                        fragmentTransaction.commitAllowingStateLoss();
                        fragmentManager.executePendingTransactions();
                    }
                } catch (Exception e){
                   e.printStackTrace();
                }

            }
        }, 500);

    }

    public void showBottomButton() {
        mLayoutLevels.setVisibility(View.VISIBLE);
        mLayoutPerformance.setVisibility(View.VISIBLE);
        viewLine.setVisibility(View.VISIBLE);
        mBackBtn.setVisibility(View.VISIBLE);

    }

    public void hideBottomButton() {
        mLayoutLevels.setVisibility(View.GONE);
        mLayoutPerformance.setVisibility(View.GONE);
        viewLine.setVisibility(View.GONE);
        mBackBtn.setVisibility(View.GONE);
    }

    /********
     * replaceWithLevelFragemnt
     ********/
    public void replaceWithLevelFragemnt() {
        /*****BottomButton Status******/
//        hideBottomButton();

        mLayoutLevels.setBackgroundResource(R.color.gray);
        mLayoutPerformance.setBackgroundResource(R.color.black);
        LevelsFragment fragment = new LevelsFragment();
        Bundle bundle = new Bundle();
        switchFragment(fragment, Constants.LEVEL_FRAGMENT, bundle);
    }

    /********
     * replaceWithPerformanceFragment
     ********/
    public void replaceWithPerformanceFragment() {
//        /*****BottomButton Status******/
        mLayoutPerformance.setBackgroundResource(R.color.gray);
        mLayoutLevels.setBackgroundResource(R.color.black);
        hideBottomButton();
        PerformanceFragment mPerformanceFragment = new PerformanceFragment();
        Bundle bundle = new Bundle();
        switchFragment(mPerformanceFragment, Constants.PERFORMANCE_FRAGMENT, bundle);

    }




      /*private void showHintDialog() {
        mHintDialog = new Dialog(ActivityHome.this);
        mHintDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mHintDialog.setContentView(R.layout.dialog_hint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mHintDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            mHintDialog.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        mHintDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mHintDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mHintDialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mHintDialog.setCanceledOnTouchOutside(true);
        mHintDialog.show();
    }*/


   /* public void AddFragment(Fragment fragment) {
        Fragment fragmentWithTag = getSupportFragmentManager().findFragmentByTag(
                fragment.getClass().getName());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragmentWithTag != null) {
            transaction.replace(R.id.fragment_container, fragmentWithTag, fragmentWithTag.getClass().getName());
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            transaction.commit();
            return;
        }
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/


}
