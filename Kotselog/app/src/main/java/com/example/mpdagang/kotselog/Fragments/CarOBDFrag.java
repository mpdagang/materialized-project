package com.example.mpdagang.kotselog.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidplot.xy.XYPlot;
import com.example.mpdagang.kotselog.Animation.Activity_Animation002_Layout;
import com.example.mpdagang.kotselog.GraphListAdapter;
import com.example.mpdagang.kotselog.MainActivity;
import com.example.mpdagang.kotselog.OBDManager;
import com.example.mpdagang.kotselog.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


public class CarOBDFrag extends Fragment {
    private static final String TAG = "CarOBDFrag";
    public GraphListAdapter mGraphListAdapter;
    public ArrayList<String> availablePids;
    public ArrayList<String> setGraph;
    public StringBuilder messages;
    public StringBuilder wholeResponse;
    public String text;

    private Button startLog;
    private Button stopLog;
    private Button testButton;

    private Spinner option1;
    private Spinner option2;
    private Spinner option3;

    private LinearLayout test;
    private Activity_Animation002_Layout testGraph;

    private TextView graphLabel1;
    private Thread stream;
    private Runnable streamer;
    public OBDManager o = new OBDManager();
    public boolean canSend = false;
    public byte[] bytes;
    public int flag = 1;

    public BroadcastReceiver cReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

             text = intent.getStringExtra("theMessage");

            messages.append(text);
            wholeResponse.append(text);

            //incomingMessages.setText(messages);
            if (text.contains(">")){
                Log.d(TAG,"this " + wholeResponse.toString() + " is the whole reply");
                double x;
                if(flag == 1) {
                    x = (double) o.calRPM(wholeResponse.toString());
                    graphLabel1.setText("value :" + x + ". ");
                    testGraph.updateGraph(x);
                    flag = 2;
                }else if(flag == 2){
                    x = (double) o.calEngineCoolantTemp(wholeResponse.toString());
                    testGraph.updateGraph3(x);
                    flag = 3;
                }else{
                    x = (double) o.calThrottlePos(wholeResponse.toString());
                    testGraph.updateGraph2(x);
                    flag = 1;
                }

                wholeResponse = new StringBuilder();
            }

            text = null;
            Log.d(TAG,"Data written on field");
        }
    };

    public CarOBDFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "CarOBDView created");
        View view = inflater.inflate(R.layout.fragment_car_obd, container, false);

        test = (LinearLayout) view.findViewById(R.id.testList);
        for(int i = 0; i < mGraphListAdapter.getCount(); i++) {
            test.addView(mGraphListAdapter.getView(i , null, test),i);
            Log.d(TAG, "added");

        }

        startLog = (Button) view.findViewById(R.id.startLogBtn);
        stopLog = (Button) view.findViewById(R.id.stopLogBtn);
        testButton = (Button) view.findViewById(R.id.testButton);

        graphLabel1 = (TextView) view.findViewById(R.id.graph1Label);


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, availablePids);
        spinnerAdapter.add(" ");



        option1 = (Spinner) view.findViewById(R.id.graph1Var1);
        option1.setAdapter(spinnerAdapter);
        option1.setSelection(spinnerAdapter.getCount()-1);
        option2 = (Spinner) view.findViewById(R.id.graph1Var2);
        option2.setAdapter(spinnerAdapter);
        option2.setSelection(spinnerAdapter.getCount()-1);
        option3 = (Spinner) view.findViewById(R.id.graph1Var3);
        option3.setAdapter(spinnerAdapter);
        option3.setSelection(spinnerAdapter.getCount()-1);



        startLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                for(int i = 0; i < mGraphListAdapter.getCount(); i++) {
                    View baby = test.getChildAt(i);
                    Activity_Animation002_Layout anotherBaby = (Activity_Animation002_Layout) baby.findViewById(R.id.pidGraph);
                    anotherBaby.resume();
                }
            }

        });

        stopLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                for(int i = 0; i < mGraphListAdapter.getCount(); i++) {
                    View baby = test.getChildAt(i);
                    Activity_Animation002_Layout anotherBaby = (Activity_Animation002_Layout) baby.findViewById(R.id.pidGraph);
                    anotherBaby.pause();
                }

            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"testButton: starting stream");
                    View baby = test.getChildAt(0);
                    testGraph = (Activity_Animation002_Layout) baby.findViewById(R.id.pidGraph);
                    streamer = new Runnable() {

                        @Override
                        public void run() {
                            canSend = true;
                            int toggle = 1;
                            while(canSend){
                                String modMessage;

                                if(toggle == 1) {
                                    modMessage = "010C1" + "\r";
                                    //modMessage = modMessage + "\r";
                                    bytes = modMessage.getBytes();
                                    ((MainActivity) getActivity()).writeToStream(bytes);
                                    toggle = 2;
                                }else if(toggle == 2){
                                    modMessage = "010B1" + "\r";
                                    //modMessage = modMessage + "\r";
                                    bytes = modMessage.getBytes();
                                    ((MainActivity) getActivity()).writeToStream(bytes);
                                    toggle = 3;
                                }else{
                                    modMessage = "01111" + "\r";
                                    //modMessage = modMessage + "\r";
                                    bytes = modMessage.getBytes();
                                    ((MainActivity) getActivity()).writeToStream(bytes);
                                    toggle = 1;
                                }



                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    };
                stream = new Thread(streamer);
                stream.start();

            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messages = new StringBuilder();
        wholeResponse = new StringBuilder();
        o = new OBDManager();

        availablePids  = o.decodeAvailable("test");
        o.calRPM("A3 0C 0E 96");
        setGraph = new ArrayList<>();
        setGraph.add("RPM");
        mGraphListAdapter = new GraphListAdapter(getActivity(), setGraph);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(cReceiver, new IntentFilter("incomingMessage"));
            Log.d(TAG, "Registered receiver");
        }catch(Exception e){
            Log.d(TAG, "Unable to register predefined Receivers");
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: called.");
        super.onPause();
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(cReceiver);
            Log.d(TAG, "Unregistered receiver");
        }catch(Exception e){
            Log.d(TAG, "Unable to unregister predefined Receivers");
        }
    }


}
