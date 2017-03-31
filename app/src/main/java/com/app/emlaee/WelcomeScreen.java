package com.app.emlaee;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.emlaee.Adapters.SplashPagerAdapter;
import com.app.emlaee.utils.Constants;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeScreen extends AppCompatActivity {

    private Integer ImageArr[] = {R.drawable.welcome_screen1, R.drawable.welcome_screen2, R.drawable.welcome_screen1};
    private ViewPager mViewPager;
    private CircleIndicator indicator;
    private SplashPagerAdapter mPagerAdapter;
    private TextView mTextViewLoginDirectly,txtSignUp;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        setSharedPrefrences();

        FindViewById();

    }

    private void FindViewById() {

        mTextViewLoginDirectly = (TextView) findViewById(R.id.login_directly_id);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        mPagerAdapter = new SplashPagerAdapter(this, ImageArr);
        mViewPager.setAdapter(mPagerAdapter);
        indicator.setViewPager(mViewPager);


        setClickListener();
    }

    private void setClickListener() {
        mTextViewLoginDirectly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(WelcomeScreen.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();


            }
        }); txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(WelcomeScreen.this, SignupActivity.class);
                startActivity(loginIntent);
                finish();


            }
        });
    }

    private void setSharedPrefrences() {
        mPref = this.getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        mEditor = mPref.edit();

        try {
            String str_UserId = mPref.getString(Constants.KEY_PRE_USER_ID, "");
            if (!str_UserId.equals("")) {
                Intent intent_home = new Intent(WelcomeScreen.this, ActivityHome.class);
                startActivity(intent_home);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
