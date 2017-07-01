package com.example.mpdagang.kotselog.Animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mpdagang.kotselog.R;

public class AnimationActivity001 extends AppCompatActivity {

    Activity_Animation001_Layout animation001_LayoutView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        animation001_LayoutView = new Activity_Animation001_Layout(this);
        setContentView(animation001_LayoutView);
    }
}
