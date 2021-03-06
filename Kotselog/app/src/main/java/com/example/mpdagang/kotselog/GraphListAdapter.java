/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename: GraphListAdapter.java
*/

package com.example.mpdagang.kotselog;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mpdagang.kotselog.Animation.Activity_Animation002_Layout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MPDagang on 03/07/2017.
 */
// Array adapter for the graph that displays sensor data
public class GraphListAdapter extends ArrayAdapter<String> {
    private static final String TAG = "GraphListAdapter";
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mPids;


    public GraphListAdapter(Context context, ArrayList<String> pids){
        super(context, R.layout.graph_adapter_view,pids);
        this.mPids = pids;
        mLayoutInflater = LayoutInflater.from(getContext());

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.graph_adapter_view, parent, false);

            String pName = mPids.get(position);
            // setup the graph animation view
            if (pName != null) {
                TextView pidName = (TextView) convertView.findViewById(R.id.pidName);
                Activity_Animation002_Layout g = (Activity_Animation002_Layout) convertView.findViewById(R.id.pidGraph);


                if (pidName != null) {
                    pidName.setText(pName);
                }
            }
        }

        Log.d(TAG, "View created");
        return convertView;
    }

}
