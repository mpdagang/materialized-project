package com.example.mpdagang.kotselog.Animation;

import com.example.mpdagang.kotselog.LogBook.XYValue;
import com.example.mpdagang.kotselog.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by MPDagang on 30/06/2017.
 */

public class Activity_Animation002_Layout extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    Thread thread = null;
    boolean canDraw = true;

    public Paint red_paintbrush_fill;
    public Paint blue_paintbrush_fill;
    public Paint green_paintbrush_fill;

    public Paint red_paintbrush_stroke;
    public Paint blue_paintbrush_stroke;
    public Paint green_paintbrush_stroke;
    public Paint white_paintbrush_stroke;
    public Paint transparent;

    public Path eraser;
    public Path graph;
    public ArrayList<XYValue> yValueHolder;
    public ArrayList<XYValue> yValueHolder1;
    public ArrayList<XYValue> yValueHolder2;

    Bitmap cherry;
    int cherry_x, cherry_y;

    double frames_per_second;
    double frame_time_seconds;
    double frame_time_ms;
    double frame_time_ns;
    double tLF, tEOR, delta_t;

    Canvas canvas;
    Random yRand = new Random();
    SurfaceHolder surfaceHolder;

    public Activity_Animation002_Layout(Context context) {
        super(context);
        //setBackgroundColor(Color.GRAY);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        cherry = BitmapFactory.decodeResource(getResources(), R.drawable.small_dot);
        cherry_x = 650;
        cherry_y = 130;

        transparent = new Paint();
        transparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser = new Path();

        frames_per_second = 15;
        frame_time_seconds = 1/frames_per_second;
        frame_time_ms = frame_time_seconds*1000;
        frame_time_ns = frame_time_ms*1000000;
        //1 sec = 1,000 milisec
        // 1 sec = 1,000,000,000 nanosec
        // 1 milisec = 1,000,000 milisec
    }

    public Activity_Animation002_Layout(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        cherry = BitmapFactory.decodeResource(getResources(), R.drawable.small_dot);
        cherry_x = 650;
        cherry_y = 130;

        transparent = new Paint();
        transparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser = new Path();

        yValueHolder = new ArrayList<>();
        yValueHolder1 = new ArrayList<>();
        yValueHolder2 = new ArrayList<>();

        frames_per_second = 10;
        frame_time_seconds = 1/frames_per_second;
        frame_time_ms = frame_time_seconds*1000;
        frame_time_ns = frame_time_ms*1000000;
    }

    public Activity_Animation002_Layout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        cherry = BitmapFactory.decodeResource(getResources(), R.drawable.small_dot);
        cherry_x = 650;
        cherry_y = 130;

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
        prepPaintBrushes();

        //yValueHolder = new ArrayList<>();
        double yVal;

        while(canDraw){
            //carry out drawing

            if(!surfaceHolder.getSurface().isValid()){
                continue;
            }

            //yVal = yRand.nextInt(2);
            //yValueHolder.add(new XYValue(0, yVal));
            canvas = surfaceHolder.lockCanvas();
            canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(), transparent);
            drawGraph();

            if(yValueHolder.size() > 260){
                yValueHolder.remove(0);
            }
            if(yValueHolder1.size() > 260){
                yValueHolder1.remove(0);
            }if(yValueHolder2.size() > 260){
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
        graph = new Path();

        int xVal = canvas.getWidth();
        double yVal = canvas.getHeight();
        graph.moveTo(xVal-1, (float) (yVal - yValueHolder.get(yValueHolder.size()-1).getY()));
        for(int i = yValueHolder.size()-1; i >= 0; i--, xVal-=10){
            graph.lineTo(xVal, (float) (yVal - yValueHolder.get(i).getY()));
        }

        this.canvas.drawPath(graph,blue_paintbrush_stroke);
        this.canvas.drawText("val: " + yValueHolder.get(yValueHolder.size()-1).getY(), 3,30, green_paintbrush_stroke);
        this.canvas.drawCircle((float) canvas.getWidth()-1, (float) (yVal - yValueHolder.get(yValueHolder.size()-1).getY()), 4, red_paintbrush_fill);

        graph = new Path();

        xVal = canvas.getWidth();
        yVal = canvas.getHeight();
        graph.moveTo(xVal-1, (float) (yVal - yValueHolder1.get(yValueHolder1.size()-1).getY()));
        for(int i = yValueHolder1.size()-1; i >= 0; i--, xVal-=10){
            graph.lineTo(xVal, (float) (yVal - yValueHolder1.get(i).getY()));
        }

        this.canvas.drawPath(graph,red_paintbrush_stroke);
        this.canvas.drawCircle((float) canvas.getWidth()-1, (float) (yVal - yValueHolder1.get(yValueHolder1.size()-1).getY()), 4, green_paintbrush_fill);

        graph = new Path();

        xVal = canvas.getWidth();
        yVal = canvas.getHeight();
        graph.moveTo(xVal-1, (float) (yVal - yValueHolder2.get(yValueHolder2.size()-1).getY()));
        for(int i = yValueHolder2.size()-1; i >= 0; i--, xVal-=10){
            graph.lineTo(xVal, (float) (yVal - yValueHolder2.get(i).getY()));
        }

        this.canvas.drawPath(graph,white_paintbrush_stroke);
        this.canvas.drawCircle((float) canvas.getWidth()-1, (float) (yVal - yValueHolder2.get(yValueHolder2.size()-1).getY()), 4, green_paintbrush_fill);

    }

    private void prepPaintBrushes(){
        red_paintbrush_fill = new Paint();
        red_paintbrush_fill.setColor(Color.RED);
        red_paintbrush_fill.setStyle(Paint.Style.FILL);

        blue_paintbrush_fill = new Paint();
        blue_paintbrush_fill.setColor(Color.BLUE);
        blue_paintbrush_fill.setStyle(Paint.Style.FILL);

        green_paintbrush_fill = new Paint();
        green_paintbrush_fill.setColor(Color.GREEN);
        green_paintbrush_fill.setStyle(Paint.Style.FILL);

        red_paintbrush_stroke = new Paint();
        red_paintbrush_stroke.setColor(Color.RED);
        red_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        red_paintbrush_stroke.setStrokeWidth(3);

        blue_paintbrush_stroke = new Paint();
        blue_paintbrush_stroke.setColor(Color.GREEN);
        blue_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        blue_paintbrush_stroke.setStrokeWidth(3);

        white_paintbrush_stroke = new Paint();
        white_paintbrush_stroke.setColor(Color.WHITE);
        white_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        white_paintbrush_stroke.setStrokeWidth(3);

        float radius = 50.0f;
        CornerPathEffect corner = new CornerPathEffect(radius);
        blue_paintbrush_stroke.setPathEffect(corner);
        red_paintbrush_stroke.setPathEffect(corner);
        white_paintbrush_stroke.setPathEffect(corner);

        green_paintbrush_stroke = new Paint();
        green_paintbrush_stroke.setColor(Color.YELLOW);
        //green_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        //green_paintbrush_stroke.setStrokeWidth(3);
        green_paintbrush_stroke.setAntiAlias(true);
        green_paintbrush_stroke.setTextSize(30);
        green_paintbrush_stroke.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public boolean updateGraph(double yVal){

        yValueHolder.add(new XYValue(0, yVal*0.15));

        return true;
    }

    public boolean updateGraph2(double yVal){

        yValueHolder1.add(new XYValue(0, yVal*3));

        return true;
    }
    public boolean updateGraph3(double yVal){

        yValueHolder2.add(new XYValue(0, yVal*2));

        return true;
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
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
