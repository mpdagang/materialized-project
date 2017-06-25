package com.example.mpdagang.kotselog;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        mSectionsStatePagerAdapter.addFragment(new NavFrag(), "Navigation");
        mSectionsStatePagerAdapter.addFragment(new StartConFrag(), "Start Connection");
        viewPager.setAdapter(mSectionsStatePagerAdapter);
    }

    public void setViewPager(int fragNumber){
        mViewPager.setCurrentItem(fragNumber);
    }
}
