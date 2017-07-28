/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename:
*/

package com.example.mpdagang.kotselog.Animation;

import com.example.mpdagang.kotselog.LogBook.XYValue;
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
    public ArrayList<ArrayList<Float>> graphAt;

    public Paint transparent;

    public Paint indicator;
    public Paint plotTracker;

    public Path eraser;

    public Path graph1;

    public ArrayList<XYValue> yValueHolder;
    public ArrayList<XYValue> yValueHolder1;
    public ArrayList<XYValue> yValueHolder2;
    public ArrayList<XYValue> yValueHolder3;

    double frames_per_second;
    double frame_time_seconds;
    double frame_time_ms;
    double frame_time_ns;
    double tLF, tEOR, delta_t;

    public float testX = 100;
    public float testY = 0;

    public int val1 = 0;
    public int val2 = 0;
    public int val3 = 0;
    public int val4 = 0;

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

        yValueHolder = new ArrayList<>();
        yValueHolder1 = new ArrayList<>();
        yValueHolder2 = new ArrayList<>();
        yValueHolder3 = new ArrayList<>();
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

            if(yValueHolder.size() > 200){
                yValueHolder.remove(0);
            }
            if(yValueHolder1.size() > 200){
                yValueHolder1.remove(0);
            }if(yValueHolder2.size() > 200){
                yValueHolder2.remove(0);
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

        int xVal = canvas.getWidth();
        double yVal = canvas.getHeight();

        graph1 = new Path();

        graph1.moveTo(xVal-1, (float) (yVal - yValueHolder.get(yValueHolder.size()-1).getY()));
        for(int i = yValueHolder.size()-1; i >= 0; i--, xVal-=11){
            graph1.lineTo(xVal, (float) (yVal - yValueHolder.get(i).getY()));
            if(xVal >= ((int) testX - 5) && xVal <= ((int) testX + 5)){
                this.canvas.drawText(Integer.toString((int)(yValueHolder.get(i).getY()/0.1 )), testX,(float) (yVal - yValueHolder.get(i).getY()), fontColor.get(0));
            }
        }

        this.canvas.drawPath(graph1,graphColor.get(0));
        this.canvas.drawCircle((float) canvas.getWidth()-1, (float) (yVal - yValueHolder.get(yValueHolder.size()-1).getY()), 4, plotTracker);

        graph1 = new Path();

        xVal = canvas.getWidth();
        yVal = canvas.getHeight();
        graph1.moveTo(xVal-1, (float) (yVal - yValueHolder1.get(yValueHolder1.size()-1).getY()));
        for(int i = yValueHolder1.size()-1; i >= 0; i--, xVal-=11){
            graph1.lineTo(xVal, (float) (yVal - yValueHolder1.get(i).getY()));
            if(xVal >= ((int) testX - 5) && xVal <= ((int) testX + 5)){
                this.canvas.drawText(Integer.toString((int)(yValueHolder1.get(i).getY()/3 )), testX,(float) (yVal - yValueHolder1.get(i).getY()), fontColor.get(1));
            }
        }

        this.canvas.drawPath(graph1,graphColor.get(1));
        this.canvas.drawCircle((float) canvas.getWidth()-1, (float) (yVal - yValueHolder1.get(yValueHolder1.size()-1).getY()), 4, plotTracker);

        graph1 = new Path();

        xVal = canvas.getWidth();
        yVal = canvas.getHeight();
        graph1.moveTo(xVal-1, (float) (yVal - yValueHolder2.get(yValueHolder2.size()-1).getY()));
        for(int i = yValueHolder2.size()-1; i >= 0; i--, xVal-=11){
            graph1.lineTo(xVal, (float) (yVal - yValueHolder2.get(i).getY()));
            if(xVal >= ((int) testX - 5) && xVal <= ((int) testX + 5)){
                this.canvas.drawText(Integer.toString((int)(yValueHolder2.get(i).getY()/3 )), testX,(float) (yVal - yValueHolder2.get(i).getY()), fontColor.get(2));
            }
        }

        this.canvas.drawPath(graph1,graphColor.get(2));
        this.canvas.drawCircle((float) canvas.getWidth()-1, (float) (yVal - yValueHolder2.get(yValueHolder2.size()-1).getY()), 4, plotTracker);

        graph1 = new Path();
        graph1.moveTo(testX, 0);
        graph1.lineTo(testX, canvas.getHeight());
        this.canvas.drawPath(graph1, indicator);

        this.canvas.drawText("RPM     : " + val1 + "rpm", 3, 38, fontColor.get(0));
       this.canvas.drawText("Throttle Position: " + val2 + "%", 3, 77, fontColor.get(1));
        this.canvas.drawText("Intake Manifold Pressure: " + val3 + "kPa", 3, 116, fontColor.get(2));
    }

    public boolean updateGraph(double yVal){
        val1 = (int) yVal;
        yValueHolder.add(new XYValue(0, yVal*0.1));
        return true;
    }

    public boolean updateGraph2(double yVal){
        val2 = (int) yVal;
        yValueHolder1.add(new XYValue(0, yVal*3));
        return true;
    }
    public boolean updateGraph3(double yVal){
        val3 = (int) yVal;
        yValueHolder2.add(new XYValue(0, yVal*3));
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
