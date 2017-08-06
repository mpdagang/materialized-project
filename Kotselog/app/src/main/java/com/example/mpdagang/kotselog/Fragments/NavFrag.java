/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename: NavFrag.java
*/

package com.example.mpdagang.kotselog.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mpdagang.kotselog.MainActivity;
import com.example.mpdagang.kotselog.R;

// Fragment that allows user to navigate to different components of the application
public class NavFrag extends Fragment {
    private static final String TAG = "NavFrag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav, container, false);

        Button btnNav1 = (Button) view.findViewById(R.id.btnNav1);
        Button btnNav2 = (Button) view.findViewById(R.id.btnNav2);
        Button btnNav3 = (Button) view.findViewById(R.id.btnNav3);

        btnNav1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Start connection fragment", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(1);
            }
        });

        btnNav2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Car information fragment", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(2);
            }
        });

        btnNav3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Car obd fragment", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(3);
            }
        });


        return view;
    }


}
