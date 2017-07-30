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
    public ArrayList<PidElement> selectedPid;
    public StringBuilder messages;
    public StringBuilder wholeResponse;
    public String text;

    private LinearLayout test;
    private Activity_Animation002_Layout testGraph;

    private TextView speedDis;
    private EditText speedSetter;

    private Spinner option1;
    private Spinner option2;
    private Spinner option3;

    private Thread stream;
    private Runnable streamer;
    public OBDManager o = new OBDManager();
    public boolean canSend = false;
    public byte[] bytes;
    public int flag = 0;
    public int toggle = 0;
    public int speed = 200;

    public BroadcastReceiver cReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            text = intent.getStringExtra("theMessage");
            wholeResponse.append(text);

            float value;

            if (text.contains(">")){
                Log.d(TAG,"this " + wholeResponse.toString() + " is the whole reply , flag:"+ flag +" toggle:"+toggle+".");

                if(flag < toggle){
                   value = o.calSensorValue(wholeResponse.toString(), selectedPid.get(flag).getId());
                   testGraph.updateGraph(value, flag);
                    flag++;
                }else if(flag > toggle){
                    value = o.calSensorValue(wholeResponse.toString(), selectedPid.get(flag).getId());
                    testGraph.updateGraph(value, flag);
                    flag = 0;
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

        option1 = (Spinner) view.findViewById(R.id.spinnerA1);
        option1.setAdapter(spinnerAdapter);
        option1.setSelection(spinnerAdapter.getCount()-1);
        option2 = (Spinner) view.findViewById(R.id.spinnerA2);
        option2.setAdapter(spinnerAdapter);
        option2.setSelection(spinnerAdapter.getCount()-1);
        option3 = (Spinner) view.findViewById(R.id.spinnerA3);
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
                            selectedPid = new ArrayList<>();
                            String modMessage;

                            Log.d(TAG, "Option 1 has: "+((PidElement)option1.getSelectedItem()).getId()+" selected");
                            Log.d(TAG, "Option 2 has: "+((PidElement)option2.getSelectedItem()).getId()+" selected");
                            Log.d(TAG, "Option 3 has: "+((PidElement)option3.getSelectedItem()).getId()+" selected");
                            selectedPid.add((PidElement)option1.getSelectedItem());
                            selectedPid.add((PidElement)option2.getSelectedItem());
                            selectedPid.add((PidElement)option3.getSelectedItem());
                            testGraph.setupGraph(selectedPid);

                            while(canSend){

                                 if(toggle == flag){
                                   modMessage = selectedPid.get(toggle).getId() + "1\r";
                                   bytes = modMessage.getBytes();
                                   ((MainActivity) getActivity()).writeToStream(bytes);
                                   if(toggle == selectedPid.size()-1){
                                       toggle = 0;
                                   }else{
                                       toggle++;
                                   }
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
