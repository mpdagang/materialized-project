package com.example.mpdagang.kotselog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mpdagang.kotselog.LogBook.LogBookActivity;
import com.example.mpdagang.kotselog.LogBook.PlotTest;


public class NavFrag extends Fragment {

    private Button btnNav1;
    private Button btnNav6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav, container, false);

        btnNav1 = (Button) view.findViewById(R.id.btnNav1);
        btnNav6 = (Button) view.findViewById(R.id.btnNav6);

        btnNav1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Going to Start connection fragment", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(1);
            }
        });

        btnNav6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to Log book activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PlotTest.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
