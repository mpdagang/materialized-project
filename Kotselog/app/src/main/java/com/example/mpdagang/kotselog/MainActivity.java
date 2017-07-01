package com.example.mpdagang.kotselog;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    //-------------------------------Variables-------------------------------
    private static final String TAG = "MainActivity";

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    public BluetoothConnectionService mBlueConServ;
    public StringBuilder mainResponse;
    public String curResponse;

    public BroadcastReceiver mainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "mainReceiver: received response from incomingMessageIntent");
            String text = intent.getStringExtra("theMessage");
            mainResponse.append(text);
            curResponse = text;
        }
    };

    //-------------------------------Functions-------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        LocalBroadcastManager.getInstance(this).registerReceiver(mainReceiver, new IntentFilter("incomingMessage"));
        mainResponse = new StringBuilder();
        curResponse = new String();
        mViewPager = (ViewPager) findViewById(R.id.container);
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

    public String getValueOfCommand(byte[] bytes){
        mainResponse = new StringBuilder();
        curResponse = new String();
        mBlueConServ.write(bytes);

        Runnable waiter = new Runnable() {
            int i = 0;
            @Override
            public void run() {
                while(!curResponse.contains(">")){
                    i=i+1;
                }
                Log.d(TAG, "whole response built" + mainResponse);
            }
        };
        new Thread(waiter).start();

        Toast.makeText(this, "Response is " + mainResponse + "message", Toast.LENGTH_SHORT).show();
        return mainResponse.toString().replaceAll("\n", " ");
    }
    //-------------------------------Classes-------------------------------
}
