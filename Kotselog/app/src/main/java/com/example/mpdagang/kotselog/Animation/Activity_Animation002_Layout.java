/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename:
*/

package com.example.mpdagang.kotselog.Animation;

import com.example.mpdagang.kotselog.LogBook.XYValue;
import com.example.mpdagang.kotselog.PidElement;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.util.ArrayList;


/**
 * Created by MPDagang on 30/06/2017.
 */

public class Activity_Animation002_Layout extends SurfaceView implements Runnable, SurfaceHolder.Callback, View.OnTouchListener{
    Thread thread = null;
    boolean canDraw = true;

    public ArrayList<Paint> graphColor;
    public ArrayList<Paint> fontColor;

    public Paint transparent;
    public Paint indicator;
    public Paint plotTracker;

    public Path eraser;
    public Path graph1;

    public ArrayList<ArrayList<Float>> graphHolder;
    public ArrayList<PidElement> selectedPid;

    double frames_per_second;
    double frame_time_seconds;
    double frame_time_ms;
    double frame_time_ns;
    double tLF, tEOR, delta_t;

    public float testX = 100;
    public float testY = 0;

    Canvas canvas;
    SurfaceHolder surfaceHolder;

    public Activity_Animation002_Layout(Context context) {
        super(context);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        transparent = new Paint();
        transparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser = new Path();

        frames_per_second = 15;
        frame_time_seconds = 1/frames_per_second;
        frame_time_ms = frame_time_seconds*1000;
        frame_time_ns = frame_time_ms*1000000;
    }

    public Activity_Animation002_Layout(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        transparent = new Paint();
        transparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser = new Path();

        this.setOnTouchListener(this);

        setGraphPaint();

        frames_per_second = 20;
        frame_time_seconds = 1/frames_per_second;
        frame_time_ms = frame_time_seconds*1000;
        frame_time_ns = frame_time_ms*1000000;
        //1 sec = 1,000 milisec
        // 1 sec = 1,000,000,000 nanosec
        // 1 milisec = 1,000,000 milisec
    }

    public Activity_Animation002_Layout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        transparent = new Paint();
        transparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser = new Path();

        frames_per_second = 15;
        frame_time_seconds = 1/frames_per_second;
        frame_time_ms = frame_time_seconds*1000;
        frame_time_ns = frame_time_ms*1000000;
    }


    @Override
    public void run() {
        tLF = System.nanoTime();
        delta_t = 0;

        while(canDraw){
            //carry out drawing
            if(!surfaceHolder.getSurface().isValid()){
                continue;
            }

            canvas = surfaceHolder.lockCanvas();
            canvas.drawPaint(transparent);
            drawGraph();

            for(int i = 0; i < graphHolder.size(); i++){
                if(graphHolder.get(i).size() > 200){
                    graphHolder.get(i).remove(0);
                }
            }

            surfaceHolder.unlockCanvasAndPost(canvas);

            tEOR = System.nanoTime();
            delta_t = frame_time_ns - (tEOR - tLF);

            try {
                if(delta_t > 0) {
                    Thread.sleep((long) (delta_t / 1000000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tLF = System.nanoTime();
        }
    }

    public void drawGraph(){

         int xVal;
         float yVal = canvas.getHeight();;
         for(int i = 0; i < graphHolder.size(); i++){
           xVal = canvas.getWidth();
           graph1 = new Path();

           graph1.moveTo(xVal-1, yVal-graphHolder.get(i).get(graphHolder.get(i).size()-1));
           for(int dataPoint = graphHolder.get(i).size()-1; dataPoint >= 0; dataPoint--, xVal-=11){
               graph1.lineTo(xVal, yVal - graphHolder.get(i).get(dataPoint));
               if(xVal >= ((int) testX - 5) && xVal <= ((int) testX + 5)){
                   // return to true value
                   this.canvas.drawText(Float.toString(graphHolder.get(i).get(dataPoint)), testX, yVal - graphHolder.get(i).get(dataPoint), fontColor.get(i));
               }
           }
           this.canvas.drawPath(graph1,graphColor.get(i));
           this.canvas.drawCircle((float) canvas.getWidth()-1, yVal-graphHolder.get(i).get(graphHolder.get(i).size()-1), 4, plotTracker);
        }

        graph1 = new Path();
        graph1.moveTo(testX, 0);
        graph1.lineTo(testX, canvas.getHeight());
        this.canvas.drawPath(graph1, indicator);

        //this.canvas.drawText("RPM     : " + val1 + "rpm", 3, 38, fontColor.get(0));
       //this.canvas.drawText("Throttle Position: " + val2 + "%", 3, 77, fontColor.get(1));
        //this.canvas.drawText("Intake Manifold Pressure: " + val3 + "kPa", 3, 116, fontColor.get(2));
    }

     public boolean updateGraph(float sensorValue, int graphNumber){
         // compute graphical value of sensorValue
       graphHolder.get(graphNumber).add(sensorValue);
        return true;
     }

    public boolean setupGraph(ArrayList<PidElement> pidHolder){
        graphHolder = new ArrayList<>();
        for(int i = 0; i < pidHolder.size(); i++){
            graphHolder.add(new ArrayList<Float>());
        }
        return true;
    }

    private void setGraphPaint(){
        int i;
        graphColor = new ArrayList<>();
        fontColor = new ArrayList<>();
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{30.0f, 10.0f}, 0);
        CornerPathEffect corner = new CornerPathEffect(50.0f);

        for(i = 0; i < 4; i++) {
            graphColor.add(new Paint());
            graphColor.get(i).setStyle(Paint.Style.STROKE);
            graphColor.get(i).setStrokeWidth(3);
            graphColor.get(i).setPathEffect(corner);

            fontColor.add(new Paint());
            fontColor.get(i).setAntiAlias(true);
            fontColor.get(i).setTextSize(35);
            fontColor.get(i).setTypeface(Typeface.DEFAULT_BOLD);
        }

        graphColor.get(0).setColor(Color.GREEN);
        graphColor.get(1).setColor(Color.RED);
        graphColor.get(2).setColor(Color.WHITE);
        graphColor.get(3).setColor(Color.YELLOW);

        fontColor.get(0).setColor(Color.GREEN);
        fontColor.get(1).setColor(Color.RED);
        fontColor.get(2).setColor(Color.WHITE);
        fontColor.get(3).setColor(Color.YELLOW);

        indicator = new Paint();
        indicator.setColor(Color.LTGRAY);
        indicator.setStyle(Paint.Style.STROKE);
        indicator.setStrokeWidth(3);
        indicator.setPathEffect(dashPathEffect);

        plotTracker = new Paint();
        plotTracker.setColor(Color.BLUE);
        plotTracker.setStyle(Paint.Style.FILL);
    }
    public void pause(){
        canDraw = false;
        while(true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;
    }

    public void resume(){
        canDraw = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {}

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    @Override
    public boolean onTouch(View v, MotionEvent m) {
        // touch event sets where the vertical indicator is placed
        testX = m.getX();
        return false;
    }
}
