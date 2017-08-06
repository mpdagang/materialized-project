/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename: CarInfoFrag.java
*/

package com.example.mpdagang.kotselog.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mpdagang.kotselog.MainActivity;
import com.example.mpdagang.kotselog.R;

// Fragment that show basic car information
public class CarInfoFrag extends Fragment {
    private static final String TAG = "CarOBDFrag";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_info, container, false);

        Button returnToNav = (Button) view.findViewById(R.id.returnToNav);

        returnToNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Return to \n main navigation fragment", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });

        return view;
    }


}
