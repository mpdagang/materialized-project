/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename: Activity_Animation002_Layout.java
*/

package com.example.mpdagang.kotselog.Animation;

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

    private static final String TAG = "Activity_Animation002_Layout";

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

    // run() executed to animate graph
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
            // condition that regulates the array sizes
            for(int i = 0; i < graphHolder.size(); i++){
                if(graphHolder.get(i).size() > 200){
                    graphHolder.get(i).remove(0);
                }
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
            // following codes maintains a smooth graph animation
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
         float yVal = canvas.getHeight();
         float tempHolder;
         int position = 38;
        // loop that update the appearance of the graphs based on the current set of data points on each graph
         for(int i = 0; i < graphHolder.size(); i++, position+=40){
           xVal = canvas.getWidth();
           graph1 = new Path();

             this.canvas.drawText("-"+selectedPid.get(i).toString()+": "+ String.format( "%.2f",graphHolder.get(i).get(graphHolder.get(i).size() - 1))+" "+selectedPid.get(i).getSymbol(), 3, position, fontColor.get(i));
             tempHolder = PidElement.getSensorGraphValue(graphHolder.get(i).get(graphHolder.get(i).size()-1),yVal,selectedPid.get(i));
             graph1.moveTo(xVal-1, yVal - tempHolder );
             this.canvas.drawCircle((float) canvas.getWidth()-1, yVal-tempHolder, 4, plotTracker);

           for(int dataPoint = graphHolder.get(i).size()-1; dataPoint >= 0; dataPoint--, xVal-=11){
               tempHolder = PidElement.getSensorGraphValue(graphHolder.get(i).get(dataPoint),yVal,selectedPid.get(i));
               graph1.lineTo(xVal, yVal - tempHolder);
               if(xVal >= ((int) testX - 5) && xVal <= ((int) testX + 5)){
                   // return to true value
                   this.canvas.drawText(String.format( "%.2f",graphHolder.get(i).get(dataPoint)), testX, yVal - tempHolder, fontColor.get(i));
               }
           }
           this.canvas.drawPath(graph1,graphColor.get(i));
        }

        graph1 = new Path();
        graph1.moveTo(testX, 0);
        graph1.lineTo(testX, canvas.getHeight());
        this.canvas.drawPath(graph1, indicator);
    }
    // function that adds a data point to a certain graph of pid data
     public boolean updateGraph(float sensorValue, int graphNumber){
        graphHolder.get(graphNumber).add(sensorValue);
        return true;
     }
    // function that sets the graph holding the data points and the pid holder
    // used to map with the sensors being streamed
    public boolean setupGraph(ArrayList<PidElement> pidHolder){
        graphHolder = new ArrayList<>();
        selectedPid = new ArrayList<>();
        for(int i = 0; i < pidHolder.size(); i++){
            graphHolder.add(new ArrayList<Float>());
            selectedPid.add(pidHolder.get(i));
        }
        return true;
    }
    // function that sets all the colors used for the graph
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
    }// stops the animation of the graph
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
    //restarts the animation of the graph
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

    //function that takes the position from the last touch in the canvas
    @Override
    public boolean onTouch(View v, MotionEvent m) {
        // touch event sets where the vertical indicator is placed
        testX = m.getX();
        return false;
    }
}
