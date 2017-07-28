/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename:
*/

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mpdagang.kotselog.Animation.Activity_Animation002_Layout;
import com.example.mpdagang.kotselog.GraphListAdapter;
import com.example.mpdagang.kotselog.MainActivity;
import com.example.mpdagang.kotselog.OBDManager;
import com.example.mpdagang.kotselog.PidElement;
import com.example.mpdagang.kotselog.R;

import java.util.ArrayList;


public class CarOBDFrag extends Fragment {
    private static final String TAG = "CarOBDFrag";
    public GraphListAdapter mGraphListAdapter;
    public ArrayList<String> setGraph;
    public StringBuilder messages;
    public StringBuilder wholeResponse;
    public String text;

    private LinearLayout test;
    private Activity_Animation002_Layout testGraph;

    private TextView speedDis;
    private EditText speedSetter;

    private Thread stream;
    private Runnable streamer;
    public OBDManager o = new OBDManager();
    public boolean canSend = false;
    public byte[] bytes;
    public int flag = 1;
    public int toggle = 1;
    public int speed = 200;

    public BroadcastReceiver cReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            text = intent.getStringExtra("theMessage");
            wholeResponse.append(text);

            if (text.contains(">")){
                Log.d(TAG,"this " + wholeResponse.toString() + " is the whole reply , flag:"+ flag +" toggle:"+toggle+".");
                /*
                if(wholeResponse.toString().contains("STOP")){
                    resetData();

                }
                * if response is No data or Stopped,
                * 1. stop request thread and notify user
                * 2. increment time delay for request
                * 3. reset adapter and notify user if it is successful,
                * 4. start thread
                *
                * */
                double value;
                /*
                * if(toggle > flag){
                *
                * }else if( flag == size && toggle == 0){
                *
                * }
                *
                * */

                if(flag == 1 && toggle == 2) {
                    value = (double) o.calRPM(wholeResponse.toString());
                    testGraph.updateGraph(value); // green
                    flag = 2;
                }
                else if(flag == 2 && toggle == 3){
                    value = (double) o.calIntakeManifoldPressure(wholeResponse.toString());
                    testGraph.updateGraph3(value); //white
                    flag = 3;
                }else if(flag == 3 && toggle == 1){
                    value = (double) o.calThrottlePos(wholeResponse.toString());
                    testGraph.updateGraph2(value); //red
                    flag = 1;
                }else{

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
    public boolean resetDatalog(){

        canSend = false;
        toggle = 1;
        flag = 1;
        speed = speed + 100;
        stream = new Thread(streamer);
        stream.start();
        return true;
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

        Button startLog = (Button) view.findViewById(R.id.startLogBtn);
        Button stopLog = (Button) view.findViewById(R.id.stopLogBtn);
        Button testButton = (Button) view.findViewById(R.id.testButton);
        Button setButton = (Button) view.findViewById(R.id.setBtn);


        speedDis = (TextView) view.findViewById(R.id.speedDisplay);
        speedDis.setText("current speed request: "+speed+"m/s");

        speedSetter = (EditText) view.findViewById(R.id.speedSet);

        ArrayAdapter<PidElement> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,((MainActivity)getActivity()).pidList);
        spinnerAdapter.add(new PidElement("null"));

        Spinner option1 = (Spinner) view.findViewById(R.id.spinnerA1);
        option1.setAdapter(spinnerAdapter);
        option1.setSelection(spinnerAdapter.getCount()-1);
        Spinner option2 = (Spinner) view.findViewById(R.id.spinnerA2);
        option2.setAdapter(spinnerAdapter);
        option2.setSelection(spinnerAdapter.getCount()-1);
        Spinner option3 = (Spinner) view.findViewById(R.id.spinnerA3);
        option3.setAdapter(spinnerAdapter);
        option3.setSelection(spinnerAdapter.getCount()-1);

        startLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    View baby = test.getChildAt(0);
                    Activity_Animation002_Layout anotherBaby = (Activity_Animation002_Layout) baby.findViewById(R.id.pidGraph);
                    anotherBaby.resume();
            }

        });

        stopLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    View baby = test.getChildAt(0);
                    Activity_Animation002_Layout anotherBaby = (Activity_Animation002_Layout) baby.findViewById(R.id.pidGraph);
                    anotherBaby.pause();
            }
        });

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speed = Integer.parseInt((speedSetter.getText().toString()));
                speedDis.setText("current speed request: "+speed+"m/s");
            }
        });


        streamer = new Runnable() {

                        @Override
                        public void run() {
                            canSend = true;
                            String modMessage;
                            /*
                            * while(canSend){
                            *   if(toggle == flag){
                            *       // request for pid number i
                            *       //  - get pid command
                            *       //  - build request
                            *       //  - send request
                            *       // increment toggle
                            *   }
                            *   if(i == number of pids being requested){
                            *       // reset i to 0
                            *   }
                            *
                            * }
                            * */
                            while(canSend){


                                if(toggle == 1 && flag == 1) {
                                    modMessage = "010C" + "1\r"; // rpm
                                    bytes = modMessage.getBytes();
                                    ((MainActivity) getActivity()).writeToStream(bytes);
                                    toggle = 2;
                                }
                                else if(toggle == 2 && flag == 2){
                                    modMessage = "010B" + "1\r"; // intake manifold pressure
                                    bytes = modMessage.getBytes();
                                    ((MainActivity) getActivity()).writeToStream(bytes);
                                    toggle = 3;
                                }
                                else if(toggle == 3 && flag == 3){
                                    modMessage = "0111" + "1\r"; // throttle position
                                    bytes = modMessage.getBytes();
                                    ((MainActivity) getActivity()).writeToStream(bytes);
                                    toggle = 1;
                                }else{

                                }

                                try {
                                    Thread.sleep(speed);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    };


        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"testButton: starting stream");
                    View baby = test.getChildAt(0);
                    testGraph = (Activity_Animation002_Layout) baby.findViewById(R.id.pidGraph);

                stream = new Thread(streamer);
                stream.start();
            }
        });

        Button returnToNav = (Button) view.findViewById(R.id.backBtn);

        returnToNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Return to \n main navigation fragment", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(0);
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

        setGraph = new ArrayList<>();
        setGraph.add("GRAPH");
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
