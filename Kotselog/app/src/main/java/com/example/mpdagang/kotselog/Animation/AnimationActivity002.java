package com.example.mpdagang.kotselog.Animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mpdagang.kotselog.MainActivity;
import com.example.mpdagang.kotselog.R;
public class AnimationActivity002 extends AppCompatActivity {

    Activity_Animation002_Layout animation002_LayoutView;
    Activity_Animation002_Layout animation002_LayoutView2;
    Activity_Animation002_Layout animation002_LayoutView3;
    Activity_Animation002_Layout animation002_LayoutView4;
    Activity_Animation002_Layout animation002_LayoutView5;
    Activity_Animation002_Layout animation002_LayoutView6;
    Activity_Animation002_Layout animation002_LayoutView7;
    Activity_Animation002_Layout animation002_LayoutView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //animation002_LayoutView = new Activity_Animation002_Layout(this);
        //setContentView(animation002_LayoutView);
        setContentView(R.layout.activity_animation002);
        animation002_LayoutView = (Activity_Animation002_Layout) findViewById(R.id.animatePane);
        animation002_LayoutView2 = (Activity_Animation002_Layout) findViewById(R.id.animatePane2);
        animation002_LayoutView3 = (Activity_Animation002_Layout) findViewById(R.id.animatePane3);
        animation002_LayoutView4 = (Activity_Animation002_Layout) findViewById(R.id.animatePane4);
        animation002_LayoutView5 = (Activity_Animation002_Layout) findViewById(R.id.animatePane5);
        animation002_LayoutView6 = (Activity_Animation002_Layout) findViewById(R.id.animatePane6);
        animation002_LayoutView7 = (Activity_Animation002_Layout) findViewById(R.id.animatePane7);
        animation002_LayoutView8 = (Activity_Animation002_Layout) findViewById(R.id.animatePane8);

    }

    @Override
    protected void onPause() {
        super.onPause();

        animation002_LayoutView.pause();
        animation002_LayoutView2.pause();
        animation002_LayoutView3.pause();
        animation002_LayoutView4.pause();
        animation002_LayoutView5.pause();
        animation002_LayoutView6.pause();
        animation002_LayoutView7.pause();
        animation002_LayoutView8.pause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        animation002_LayoutView.resume();
        animation002_LayoutView2.resume();
        animation002_LayoutView3.resume();
        animation002_LayoutView4.resume();
        animation002_LayoutView5.resume();
        animation002_LayoutView6.resume();
        animation002_LayoutView7.resume();
        animation002_LayoutView8.resume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
