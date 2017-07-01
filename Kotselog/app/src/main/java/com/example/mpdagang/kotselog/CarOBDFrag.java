package com.example.mpdagang.kotselog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.xy.XYPlot;

import java.util.ArrayList;


public class CarOBDFrag extends Fragment {
    private static final String TAG = "CarOBDFrag";
    public ArrayList<XYPlot> sensorPlotList = new ArrayList<>();

    public CarOBDFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "CarOBDView created");
        View view = inflater.inflate(R.layout.fragment_car_obd, container, false);

        return view;
    }
    // list possible pids


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
