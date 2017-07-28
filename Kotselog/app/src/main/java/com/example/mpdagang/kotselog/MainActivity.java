/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename:
*/

package com.example.mpdagang.kotselog;

import android.bluetooth.BluetoothDevice;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mpdagang.kotselog.Fragments.CarInfoFrag;
import com.example.mpdagang.kotselog.Fragments.CarOBDFrag;
import com.example.mpdagang.kotselog.Fragments.NavFrag;
import com.example.mpdagang.kotselog.Fragments.StartConFrag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //-------------------------------Variables-------------------------------
    private static final String TAG = "MainActivity";

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    public BluetoothConnectionService mBlueConServ;
    public StringBuilder mainResponse;
    public String curResponse;
    public HashMap<String,PidElement> pidHolder;
    public ArrayList<PidElement> pidList;

    //-------------------------------Functions-------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        mainResponse = new StringBuilder();
        curResponse = new String();
        mViewPager = (ViewPager) findViewById(R.id.container);
        pidList = new ArrayList<>();
        pidHolder = new HashMap<>();
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        mSectionsStatePagerAdapter.addFragment(new NavFrag(), "Navigation");
        mSectionsStatePagerAdapter.addFragment(new StartConFrag(), "Start Connection");
        mSectionsStatePagerAdapter.addFragment(new CarInfoFrag(), "Show Car Details");
        mSectionsStatePagerAdapter.addFragment(new CarOBDFrag(), "Start Data Log");
        viewPager.setAdapter(mSectionsStatePagerAdapter);
    }

    public void setViewPager(int fragNumber){
        mViewPager.setCurrentItem(fragNumber);
    }

    public void startBluetoothConnection(BluetoothDevice device, UUID ssdUuid){
        mBlueConServ.startClient(device, ssdUuid);
    }

    public void setBluetoothConnectionService(){
        mBlueConServ = new BluetoothConnectionService(MainActivity.this);
    }
    public void writeToStream(byte[] bytes){
        mBlueConServ.write(bytes);
    }

    public void setupPids( ArrayList<String> availablePids){
        //this.pidList = availablePids;
        for(int i = 0; i < availablePids.size(); i++){
            this.pidHolder.put(availablePids.get(i), new PidElement(availablePids.get(i)));
            this.pidList.add(new PidElement(availablePids.get(i)));
        }

    }
}
