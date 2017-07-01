package com.example.mpdagang.kotselog.Animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mpdagang.kotselog.R;

public class AnimationActivity002 extends AppCompatActivity {

    Activity_Animation002_Layout animation002_LayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //animation002_LayoutView = new Activity_Animation002_Layout(this);
        //setContentView(animation002_LayoutView);
        setContentView(R.layout.activity_animation002);
        animation002_LayoutView = (Activity_Animation002_Layout) findViewById(R.id.animatePane);

    }

    @Override
    protected void onPause() {
        super.onPause();

        animation002_LayoutView.pause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        animation002_LayoutView.resume();
    }
}
