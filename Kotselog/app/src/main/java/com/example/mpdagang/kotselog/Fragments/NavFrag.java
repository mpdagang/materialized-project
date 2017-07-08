package com.example.mpdagang.kotselog.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mpdagang.kotselog.Animation.AnimationActivity001;
import com.example.mpdagang.kotselog.Animation.AnimationActivity002;
import com.example.mpdagang.kotselog.LogBook.LogBookActivity;
import com.example.mpdagang.kotselog.LogBook.PlotTest;
import com.example.mpdagang.kotselog.MainActivity;
import com.example.mpdagang.kotselog.R;


public class NavFrag extends Fragment {

    private static final String TAG = "NavFrag";
    private Button btnNav1;
    private Button btnNav2;
    private Button btnNav3;
    private Button btnNav6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav, container, false);

        btnNav1 = (Button) view.findViewById(R.id.btnNav1);
        btnNav2 = (Button) view.findViewById(R.id.btnNav2);
        btnNav3 = (Button) view.findViewById(R.id.btnNav3);
        btnNav6 = (Button) view.findViewById(R.id.btnNav6);

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

        btnNav6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to Log book activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AnimationActivity002.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
